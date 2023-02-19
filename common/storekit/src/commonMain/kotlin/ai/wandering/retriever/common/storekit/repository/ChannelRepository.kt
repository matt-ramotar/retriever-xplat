package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun stream(userId: String): Flow<List<Channel.Output.Unpopulated>?>
    suspend fun get(channelId: String): Channel.Output.Populated?
}