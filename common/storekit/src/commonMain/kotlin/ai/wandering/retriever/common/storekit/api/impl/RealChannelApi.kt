package ai.wandering.retriever.common.storekit.api.impl

import ai.wandering.retriever.common.storekit.api.ChannelApi
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RealChannelApi(private val client: HttpClient) : ChannelApi {
    override suspend fun getChannel(channelId: String): RequestResult<Channel.Network> = try {
        val response = client.get("${Endpoints.CHANNEL}/$channelId")
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

}