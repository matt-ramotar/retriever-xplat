package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun streamAll(userId: String): Flow<List<Channel.Output.Populated>?>
    fun stream(channelId: String): Flow<Channel.Output.Populated?>
    suspend fun get(channelId: String): Channel.Output.Populated?
}