package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

@Serializable
data class Channels(
    val channels: List<Channel.Network.Populated>
)