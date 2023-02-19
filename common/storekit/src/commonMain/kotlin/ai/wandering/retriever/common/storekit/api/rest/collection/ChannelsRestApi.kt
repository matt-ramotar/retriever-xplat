package ai.wandering.retriever.common.storekit.api.rest.collection

import ai.wandering.retriever.common.storekit.entity.Channels
import ai.wandering.retriever.common.storekit.result.RequestResult

interface ChannelsRestApi {
    suspend fun get(userId: String): RequestResult<Channels>
}