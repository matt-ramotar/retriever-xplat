package ai.wandering.retriever.common.storekit.api.impl.paging

import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.entity.paging.PagingRequest
import ai.wandering.retriever.common.storekit.api.paging.PagingResult
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.entity.UserAction
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RealUserActionPagingApi(private val client: HttpClient) : UserActionPagingApi {
    override val pageSize: Int = 20
    override val prefetchDistance: Int = pageSize
    override val maxSize: Int = Int.MAX_VALUE
    override suspend fun get(type: PagingType, pages: List<PagingResult.Page<String, UserAction.Network>>): PagingResult = try {
        client.post(Endpoints.PAGING_NOTE) {
            setBody(PagingRequest(type, pages, pageSize))
            contentType(ContentType.Application.Json)
        }.body()
    } catch (error: Throwable) {
        PagingResult.Error(error)
    }
}