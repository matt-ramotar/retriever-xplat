package ai.wandering.retriever.common.storekit.entity.paging

import kotlinx.serialization.Serializable

@Serializable
data class PagingRequest<Id : Any>(
    val pageId: Id,
    val limit: Int,
    val type: PagingType,
)