package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.result.RequestResult

interface ChannelApi {
    suspend fun getChannel(channelId: String): RequestResult<Channel.Network>
}