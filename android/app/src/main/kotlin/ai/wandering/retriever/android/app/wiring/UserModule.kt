package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.android.common.scoping.UserScope
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelsRestApi
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.repository.UserNotificationsRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealUserNotificationsRepository
import ai.wandering.retriever.common.storekit.store.ChannelsStore
import ai.wandering.retriever.common.storekit.store.Stores
import ai.wandering.retriever.common.storekit.store.collection.ChannelsStoreProvider
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
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
    @Named(Stores.Channels)
    fun provideChannelsStore(
        api: ChannelsRestApi,
        db: RetrieverDatabase
    ): ChannelsStore = ChannelsStoreProvider(api, db).provideMutableStore()
}