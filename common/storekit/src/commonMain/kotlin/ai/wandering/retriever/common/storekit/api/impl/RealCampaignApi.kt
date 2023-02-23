package ai.wandering.retriever.common.storekit.api.impl

import ai.wandering.retriever.common.storekit.api.CampaignApi
import ai.wandering.retriever.common.storekit.entity.Campaign
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RealCampaignApi(private val client: HttpClient) : CampaignApi {
    override suspend fun get(userId: String, type: Campaign.Type): RequestResult<Campaign.Network> = try {
        val response = client.get(Endpoints.campaign(userId, type))
        println(response)
        val network = response.body<Campaign.Network>()
        println(network)
        RequestResult.Success(network)
    } catch (error: Throwable) {
        println("ERROR")
        println(error.message)
        RequestResult.Exception(error)
    }


}