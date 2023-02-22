package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.impl.Collection
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelsRestApi
import ai.wandering.retriever.common.storekit.entity.Channels
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class RealChannelsRestApi(private val client: HttpClient) : ChannelsRestApi {
    override suspend fun get(userId: String): RequestResult<Channels> = try {
        val endpoint = Endpoints.collection(userId, Collection.Channel, populate = true)
        val response = client.get(endpoint)
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}