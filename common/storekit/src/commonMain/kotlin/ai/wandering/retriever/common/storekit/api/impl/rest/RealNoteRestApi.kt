package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.impl.Collection
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.rest.single.NoteRestApi
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RealNoteRestApi(private val client: HttpClient) : NoteRestApi {
    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): RequestResult<Note.Network.Populated> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(input: Note.Output.Unpopulated): RequestResult<Boolean> = try {
        val response = client.post(Endpoints.upsert(collection = Collection.Note)) {
            setBody(input)
            contentType(ContentType.Application.Json)
        }
        val updated: Note.Network.Unpopulated = response.body()
        RequestResult.Success(true)
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}