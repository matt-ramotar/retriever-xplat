package ai.wandering.retriever.android.common.scoping

import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.repository.ChannelRepository
import ai.wandering.retriever.common.storekit.repository.ChannelsManager
import ai.wandering.retriever.common.storekit.repository.UserNotificationsRepository
import ai.wandering.retriever.common.storekit.store.ChannelStore
import ai.wandering.retriever.common.storekit.store.ChannelsStore
import ai.wandering.retriever.common.storekit.store.Stores
import com.squareup.anvil.annotations.ContributesTo
import javax.inject.Named

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: AuthenticatedUser
    val api: RetrieverApi
    val userNotificationsRepository: UserNotificationsRepository

    @Named(Stores.Collection.Channel)
    fun channelsStore(): ChannelsStore

    @Named(Stores.Single.Channel)
    fun channelStore(): ChannelStore

    val channelRepository: ChannelRepository
    val channelsManager: ChannelsManager
}