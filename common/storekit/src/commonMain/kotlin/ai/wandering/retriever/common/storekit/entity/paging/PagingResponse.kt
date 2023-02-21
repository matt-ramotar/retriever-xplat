package ai.wandering.retriever.common.storekit.entity.paging

import kotlinx.serialization.Serializable


sealed class PagingResponse<out Key : Any, out Network : Any> {
    /**
     * @param offset Int ID of first object in page
     */
    @Serializable
    data class Data<out Key : Any, out Network : Any>(
        val pageId: Int,
        val objects: List<Network>,
        val prevPageId: Int?,
        val nextPageId: Int?,
        val totalObjects: Int,
        val totalPages: Int,
        val offset: Int
    ) : PagingResponse<Key, Network>()

    object Error : PagingResponse<Nothing, Nothing>()
}





