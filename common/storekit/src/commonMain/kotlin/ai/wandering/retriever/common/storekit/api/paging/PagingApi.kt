package ai.wandering.retriever.common.storekit.api.paging

import ai.wandering.retriever.common.storekit.entity.paging.Page
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.result.RequestResult

interface PagingApi<Key : Any, Network : Any> {

    val userId: String
    val pageSize: Int
    val prefetchDistance: Int
    val maxSize: Int
    suspend fun get(
        key: Key,
        type: PagingType,
    ): RequestResult<Page<Key, Network>>
}


