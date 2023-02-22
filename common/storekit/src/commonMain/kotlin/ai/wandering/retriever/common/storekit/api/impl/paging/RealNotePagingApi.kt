package ai.wandering.retriever.common.storekit.api.impl.paging

import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.paging.collection.NotePagingApi
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.entity.paging.PagingRequest
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class RealNotePagingApi(private val client: HttpClient) : NotePagingApi {
    override val limit: Int = 20
    override val prefetchDistance: Int = limit
    override val maxSize: Int = Int.MAX_VALUE
    override suspend fun get(key: Int, type: PagingType, query: Json?): RequestResult<PagingResponse<Int, Note.Network.Populated>> = try {
        client.post(Endpoints.PAGING_NOTE) {
            setBody(PagingRequest(key, limit, type))
            contentType(ContentType.Application.Json)
        }.body()
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}