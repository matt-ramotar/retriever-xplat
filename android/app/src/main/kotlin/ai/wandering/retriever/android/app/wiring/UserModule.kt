package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.channels.RealChannelsManager
import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.android.common.scoping.UserScope
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelsRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.ChannelRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.NoteRestApi
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.repository.ChannelRepository
import ai.wandering.retriever.common.storekit.repository.ChannelsManager
import ai.wandering.retriever.common.storekit.repository.NoteRepository
import ai.wandering.retriever.common.storekit.repository.UserNotificationsRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealChannelRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealNoteRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealUserNotificationsRepository
import ai.wandering.retriever.common.storekit.store.ChannelStore
import ai.wandering.retriever.common.storekit.store.ChannelsStore
import ai.wandering.retriever.common.storekit.store.NoteStore
import ai.wandering.retriever.common.storekit.store.Stores
import ai.wandering.retriever.common.storekit.store.collection.ChannelsStoreProvider
import ai.wandering.retriever.common.storekit.store.single.channel.PopulatedChannelStoreProvider
import ai.wandering.retriever.common.storekit.store.single.note.NoteStoreProvider
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@Module
@ContributesTo(UserScope::class)
object UserModule {
    @SingleIn(UserScope::class)
    @Provides
    fun provideUserNotificationsRepository(
        socket: UserNotificationsSocketApi,
        user: AuthenticatedUser
    ): UserNotificationsRepository = RealUserNotificationsRepository(socket, user)

    @SingleIn(UserScope::class)
    @Provides
    @Named(Stores.Collection.Channel)
    fun provideChannelsStore(
        api: ChannelsRestApi,
        db: RetrieverDatabase
    ): ChannelsStore = ChannelsStoreProvider(api, db).provideMutableStore()

    @SingleIn(UserScope::class)
    @Provides
    @Named(Stores.Single.Channel)
    fun provideChannelStore(
        api: ChannelRestApi,
        db: RetrieverDatabase
    ): ChannelStore = PopulatedChannelStoreProvider(api, db).provideMutableStore()

    @SingleIn(UserScope::class)
    @Provides
    fun provideChannelRepository(
        @Named(Stores.Single.Channel) channelStore: ChannelStore,
        @Named(Stores.Collection.Channel) channelsStore: ChannelsStore,
    ): ChannelRepository = RealChannelRepository(channelStore, channelsStore)

    @SingleIn(UserScope::class)
    @Provides
    fun provideChannelsManager(
        user: AuthenticatedUser,
        repository: ChannelRepository
    ): ChannelsManager = RealChannelsManager(user, repository, CoroutineScope(Dispatchers.Default))

    @SingleIn(UserScope::class)
    @Provides
    @Named(Stores.Single.Note)
    fun provideNoteStore(
        api: NoteRestApi,
        db: RetrieverDatabase
    ): NoteStore = NoteStoreProvider(api, db).provideMutableStore()

    @Provides
    fun provideNoteRepository(
        @Named(Stores.Single.Note) noteStore: NoteStore,
    ): NoteRepository = RealNoteRepository(noteStore, CoroutineScope(Dispatchers.Default))
}