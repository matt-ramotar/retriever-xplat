package ai.wandering.retriever.common.storekit.entity.paging

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class PagingRequest<Id : Any>(
    val pageId: Id,
    val limit: Int,
    val type: PagingType,
    @Contextual val query: Json? = null,
)