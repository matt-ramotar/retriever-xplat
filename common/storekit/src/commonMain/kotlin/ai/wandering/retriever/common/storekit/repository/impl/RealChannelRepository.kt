package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.repository.ChannelRepository
import ai.wandering.retriever.common.storekit.store.ChannelStore
import ai.wandering.retriever.common.storekit.store.ChannelsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import org.mobilenativefoundation.store.store5.StoreReadRequest

class RealChannelRepository(
    private val channelStore: ChannelStore,
    private val channelsStore: ChannelsStore
) : ChannelRepository {
    override fun stream(userId: String): Flow<List<Channel.Output.Unpopulated>?> = channelFlow {
        channelsStore.stream<Channel.Output.Unpopulated>(StoreReadRequest.cached(userId, refresh = true)).collectLatest {
            send(it.dataOrNull())
        }

    }

    override suspend fun get(channelId: String): Channel.Output.Populated? =
        channelStore.stream<Channel.Output.Populated>(StoreReadRequest.cached(channelId, refresh = true)).firstOrNull()?.dataOrNull()
}