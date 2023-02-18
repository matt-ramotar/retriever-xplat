package ai.wandering.retriever.common.storekit.entity.paging

import kotlinx.serialization.Serializable

@Serializable
data class Page<Key : Any, Network : Any>(
    val key: Key,
    val items: List<Network> = listOf(),
    val prevKey: Key? = null,
    val nextKey: Key? = null
)
