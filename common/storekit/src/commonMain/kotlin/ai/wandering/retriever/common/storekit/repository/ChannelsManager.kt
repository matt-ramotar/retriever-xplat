package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChannelsManager {
    val channels: StateFlow<List<Channel.Output.Populated>?>
    fun loadChannels(userId: String)
    fun streamChannel(channelId: String): Flow<Channel.Output.Populated?>
}