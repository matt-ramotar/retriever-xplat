package ai.wandering.retriever.android.common.channels

import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.repository.ChannelRepository
import ai.wandering.retriever.common.storekit.repository.ChannelsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RealChannelsManager(
    user: AuthenticatedUser,
    private val repository: ChannelRepository,
    private val scope: CoroutineScope
) : ChannelsManager {

    private val mutableStateFlow: MutableStateFlow<List<Channel.Output.Unpopulated>?> = MutableStateFlow(null)
    override val channels: StateFlow<List<Channel.Output.Unpopulated>?> = mutableStateFlow

    init {
        loadChannels(user.id)
    }

    override fun loadChannels(userId: String) {
        scope.launch {
            println("Hitting")
            repository.stream(userId).collectLatest { channels ->
                mutableStateFlow.value = channels
                println("Set channels: $channels")
                channels?.forEach { channel ->
                    loadChannel(channel.id)
                    println("Loaded channel: ${channel.id}")
                }
            }
        }
    }

    private suspend fun loadChannel(channelId: String) {
        repository.get(channelId)
    }
}
