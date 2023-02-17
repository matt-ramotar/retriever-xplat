package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelRestApi
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RealChannelRestApi(private val client: HttpClient) : ChannelRestApi {
    override suspend fun create(request: Channel.Request.Create): RequestResult<Channel.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): RequestResult<Channel.Network> = try {
        val response = client.get("${Endpoints.CHANNEL}/$id")
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    override suspend fun update(request: Channel.Request.Update): RequestResult<Channel.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

}