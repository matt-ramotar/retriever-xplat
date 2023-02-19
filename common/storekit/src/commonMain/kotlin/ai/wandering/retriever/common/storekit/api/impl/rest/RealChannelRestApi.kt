package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.impl.Collection
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.rest.single.ChannelRestApi
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RealChannelRestApi(private val client: HttpClient) : ChannelRestApi {
    override suspend fun get(id: String): RequestResult<Channel.Network.Populated> = try {
        val response = client.get(Endpoints.single(id = id, collection = Collection.Channel, populate = true))
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    override suspend fun upsert(input: Channel.Output.Node): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }
}