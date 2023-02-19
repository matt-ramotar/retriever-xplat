package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.impl.RealRetrieverApi
import ai.wandering.retriever.common.storekit.api.impl.auth.RealAuthApi
import ai.wandering.retriever.common.storekit.api.impl.auth.RealDemoSignInApi
import ai.wandering.retriever.common.storekit.api.impl.auth.RealOneTapSignInApi
import ai.wandering.retriever.common.storekit.api.impl.paging.RealNotePagingApi
import ai.wandering.retriever.common.storekit.api.impl.paging.RealUserActionPagingApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealChannelRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealGraphRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealMentionRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealNoteRestApi
import ai.wandering.retriever.common.storekit.api.impl.rest.RealTagRestApi
import ai.wandering.retriever.common.storekit.api.impl.socket.RealUserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.api.paging.collection.NotePagingApi
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.api.rest.auth.AuthApi
import ai.wandering.retriever.common.storekit.api.rest.auth.DemoSignInApi
import ai.wandering.retriever.common.storekit.api.rest.auth.OneTapSignInApi
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.GraphRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.MentionRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.NoteRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.TagRestApi
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.networking.HttpClientProvider
import ai.wandering.retriever.common.storekit.repository.auth.AuthRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealAuthRepository
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

@Module
@ContributesTo(AppScope::class)
object AppModule {

    private val httpClient = HttpClientProvider().provide()


    @Provides
    fun provideSerializer(): Json = Json { ignoreUnknownKeys = true; isLenient = true; }

    @Provides
    fun provideOneTapSignInApi(): OneTapSignInApi = RealOneTapSignInApi(httpClient)

    @Provides
    fun provideDemoSignInApi(serializer: Json): DemoSignInApi = RealDemoSignInApi(serializer, httpClient)

    @Provides
    fun provideAuthApi(oneTapSignInApi: OneTapSignInApi, demoSignInApi: DemoSignInApi): AuthApi = RealAuthApi(httpClient, oneTapSignInApi, demoSignInApi)

    @Provides
    fun provideChannelRestApi(): ChannelRestApi = RealChannelRestApi(httpClient)

    @Provides
    fun provideGraphRestApi(): GraphRestApi = RealGraphRestApi(httpClient)

    @Provides
    fun provideMentionRestApi(): MentionRestApi = RealMentionRestApi(httpClient)

    @Provides
    fun provideNoteRestApi(): NoteRestApi = RealNoteRestApi(httpClient)

    @Provides
    fun provideNotePagingApi(): NotePagingApi = RealNotePagingApi(httpClient)

    @Provides
    fun provideTagRestApi(): TagRestApi = RealTagRestApi(httpClient)

    @Provides
    fun provideUserActionPagingApi(): UserActionPagingApi = RealUserActionPagingApi(httpClient)

    @Provides
    fun provideUserNotificationsSocketApi(serializer: Json): UserNotificationsSocketApi = RealUserNotificationsSocketApi(
        serializer = serializer,
        socket = Socket(
            endpoint = Endpoints.SOCKET,
            scope = CoroutineScope(Dispatchers.IO)
        )
    )

    @Provides
    fun provideRetrieverApi(
        authApi: AuthApi,
        channelRestApi: ChannelRestApi,
        graphRestApi: GraphRestApi,
        mentionRestApi: MentionRestApi,
        noteRestApi: NoteRestApi,
        notePagingApi: NotePagingApi,
        tagRestApi: TagRestApi,
        userActionPagingApi: UserActionPagingApi,
        userNotificationsSocketApi: UserNotificationsSocketApi
    ): RetrieverApi = RealRetrieverApi(
        auth = authApi,
        channel = channelRestApi,
        graph = graphRestApi,
        mention = mentionRestApi,
        note = noteRestApi,
        notePaging = notePagingApi,
        tag = tagRestApi,
        userActionPaging = userActionPagingApi,
        userNotificationsSocket = userNotificationsSocketApi
    )

    @Provides
    fun provideAuthRepository(authApi: AuthApi): AuthRepository = RealAuthRepository(authApi)
}
