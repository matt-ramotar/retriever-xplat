package ai.wandering.retriever.common.storekit.api.paging

import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.serialization.json.Json

interface PagingApi<Key : Any, Network : Any> {
    val limit: Int
    val prefetchDistance: Int
    val maxSize: Int
    suspend fun get(
        key: Key,
        type: PagingType,
        query: Json? = null,
    ): RequestResult<PagingResponse<Key, Network>>
}


