package ai.wandering.retriever.common.storekit.entity.paging

import kotlinx.serialization.Serializable

@Serializable
data class PagingRequest<Key : Any>(
    val key: Key,
    val type: PagingType,
    val userId: String,
    val pageSize: Int,
)