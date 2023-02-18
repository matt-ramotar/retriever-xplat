package ai.wandering.retriever.common.storekit.api.impl.paging

import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.entity.paging.Page
import ai.wandering.retriever.common.storekit.entity.paging.PagingRequest
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.api.paging.collection.NotePagingApi
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RealNotePagingApi(private val client: HttpClient, override val userId: String) : NotePagingApi {
    override val pageSize: Int = 20
    override val prefetchDistance: Int = pageSize
    override val maxSize: Int = Int.MAX_VALUE
    override suspend fun get(key: String, type: PagingType): RequestResult<Page<String, Note.Network>> = try {
        val request = PagingRequest(
            key = key,
            type = type,
            userId = userId,
            pageSize = pageSize,
        )

        val response = client.post(Endpoints.PAGING_NOTE) {
            setBody(request)
            contentType(ContentType.Application.Json)
        }

        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}