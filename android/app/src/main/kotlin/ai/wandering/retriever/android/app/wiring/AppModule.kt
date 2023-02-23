package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.api.CampaignApi
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.impl.RealCampaignApi
import ai.wandering.retriever.common.storekit.api.impl.RealRetrieverApi
import ai.wandering.retriever.common.storekit.api.impl.auth.RealAuthApi
import ai.wandering.retriever.common.storekit.api.impl.auth.RealDemoSignInApi
import ai.wandering.retriever.common.storekit.api.impl.auth.RealOneTapSignInApi
import ai.wandering.retriever.common.storekit.api.impl.paging.RealNotePagingApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealChannelRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealChannelsRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealGraphRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealMentionRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealNoteRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealTagRestApi
import ai.wandering.retriever.common.storekit.api.impl.socket.RealUserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.api.paging.collection.NotePagingApi
import ai.wandering.retriever.common.storekit.api.rest.auth.AuthApi
import ai.wandering.retriever.common.storekit.api.rest.auth.DemoSignInApi
import ai.wandering.retriever.common.storekit.api.rest.auth.OneTapSignInApi
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelsRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.ChannelRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.GraphRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.MentionRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.NoteRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.TagRestApi
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.entity.Component
import ai.wandering.retriever.common.storekit.networking.HttpClientProvider
import ai.wandering.retriever.common.storekit.repository.CampaignRepository
import ai.wandering.retriever.common.storekit.repository.auth.AuthRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealAuthRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealCampaignRepository
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Module
@ContributesTo(AppScope::class)
object AppModule {

    @SingleIn(AppScope::class)
    @Provides
    fun provideHttpClient(): HttpClient = HttpClientProvider().provide()

    @SingleIn(AppScope::class)
    @Provides
    fun provideSerializer(): Json = Json {
        ignoreUnknownKeys = true; isLenient = true; serializersModule = SerializersModule {
        polymorphic(Component::class) {
            subclass(Component.Text::class)
            subclass(Component.Row::class)
            subclass(Component.Column::class)
        }
    }
    }

    @Provides
    fun provideOneTapSignInApi(httpClient: HttpClient): OneTapSignInApi = RealOneTapSignInApi(httpClient)

    @Provides
    fun provideDemoSignInApi(httpClient: HttpClient, serializer: Json): DemoSignInApi = RealDemoSignInApi(serializer, httpClient)

    @Provides
    fun provideAuthApi(httpClient: HttpClient, oneTapSignInApi: OneTapSignInApi, demoSignInApi: DemoSignInApi): AuthApi = RealAuthApi(httpClient, oneTapSignInApi, demoSignInApi)

    @Provides
    fun provideChannelRestApi(httpClient: HttpClient): ChannelRestApi = RealChannelRestApi(httpClient)

    @Provides
    fun provideChannelsRestApi(httpClient: HttpClient): ChannelsRestApi = RealChannelsRestApi(httpClient)

    @Provides
    fun provideGraphRestApi(httpClient: HttpClient): GraphRestApi = RealGraphRestApi(httpClient)

    @Provides
    fun provideMentionRestApi(httpClient: HttpClient): MentionRestApi = RealMentionRestApi(httpClient)

    @Provides
    fun provideNoteRestApi(httpClient: HttpClient): NoteRestApi = RealNoteRestApi(httpClient)

    @Provides
    fun provideNotePagingApi(httpClient: HttpClient): NotePagingApi = RealNotePagingApi(httpClient)

    @Provides
    fun provideTagRestApi(httpClient: HttpClient): TagRestApi = RealTagRestApi(httpClient)

    @SingleIn(AppScope::class)
    @Provides
    fun provideUserNotificationsSocketApi(serializer: Json): UserNotificationsSocketApi {
        val socket = Socket(
            endpoint = Endpoints.SOCKET,
            scope = CoroutineScope(Dispatchers.Default)
        )

        socket.connect()

        return RealUserNotificationsSocketApi(
            serializer = serializer,
            socket = socket
        )
    }

    @Provides
    fun provideRetrieverApi(
        authApi: AuthApi,
        channelRestApi: ChannelRestApi,
        channelsRestApi: ChannelsRestApi,
        graphRestApi: GraphRestApi,
        mentionRestApi: MentionRestApi,
        noteRestApi: NoteRestApi,
        notePagingApi: NotePagingApi,
        tagRestApi: TagRestApi,
        userNotificationsSocketApi: UserNotificationsSocketApi
    ): RetrieverApi = RealRetrieverApi(
        auth = authApi,
        channel = channelRestApi,
        channels = channelsRestApi,
        graph = graphRestApi,
        mention = mentionRestApi,
        note = noteRestApi,
        notePaging = notePagingApi,
        tag = tagRestApi,
        userNotificationsSocket = userNotificationsSocketApi
    )

    @Provides
    fun provideAuthRepository(authApi: AuthApi): AuthRepository = RealAuthRepository(authApi)

    @Provides
    fun provideCampaignApi(httpClient: HttpClient): CampaignApi = RealCampaignApi(httpClient)

    @Provides
    fun provideCampaignRepository(serializer: Json, campaignApi: CampaignApi): CampaignRepository = RealCampaignRepository(serializer, campaignApi)
}
