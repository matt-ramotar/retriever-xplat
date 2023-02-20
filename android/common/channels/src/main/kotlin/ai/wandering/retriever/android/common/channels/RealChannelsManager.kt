package ai.wandering.retriever.android.common.channels

import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.repository.ChannelRepository
import ai.wandering.retriever.common.storekit.repository.ChannelsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RealChannelsManager(
    user: AuthenticatedUser,
    private val repository: ChannelRepository,
    private val scope: CoroutineScope
) : ChannelsManager {

    private val mutableStateFlow: MutableStateFlow<List<Channel.Output.Populated>?> = MutableStateFlow(null)
    override val channels: StateFlow<List<Channel.Output.Populated>?> = mutableStateFlow

    init {
        loadChannels(user.id)
    }

    override fun loadChannels(userId: String) {
        scope.launch {
            repository.streamAll(userId).collectLatest { channels ->
                mutableStateFlow.value = channels
            }
        }
    }

    override fun streamChannel(channelId: String): Flow<Channel.Output.Populated?> = repository.stream(channelId)

    private suspend fun loadChannel(channelId: String) {
        val channel = repository.get(channelId)

    }
}
