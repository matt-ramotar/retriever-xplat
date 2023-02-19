package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.Channel
import kotlinx.coroutines.flow.StateFlow

interface ChannelsManager {
    val channels: StateFlow<List<Channel.Output.Unpopulated>?>
    fun loadChannels(userId: String)
}