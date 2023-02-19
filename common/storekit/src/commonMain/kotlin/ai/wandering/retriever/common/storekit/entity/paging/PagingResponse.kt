package ai.wandering.retriever.common.storekit.entity.paging

import kotlinx.serialization.Serializable


sealed class PagingResponse<out Key : Any, out Network : Any> {
    @Serializable
    data class Data<Key : Any, Network : Any>(
        val page: Page<Key, Network>,
        val metadata: Metadata? = null
    ) : PagingResponse<Key, Network>() {
        @Serializable
        data class Page<Key : Any, Network : Any>(
            val id: Key,
            val objects: List<Network>? = null,
            val prevId: Key? = null,
            val nextId: Key? = null,
        )

        @Serializable
        data class Metadata(
            val totalObjects: Int,
            val totalPages: Int,
            val offset: Int,
        )
    }

    object Error : PagingResponse<Nothing, Nothing>()
}





