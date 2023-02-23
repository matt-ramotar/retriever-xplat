package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.api.CampaignApi
import ai.wandering.retriever.common.storekit.entity.Campaign
import ai.wandering.retriever.common.storekit.entity.Content
import ai.wandering.retriever.common.storekit.repository.CampaignRepository
import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.serialization.json.Json

class RealCampaignRepository(private val serializer: Json, private val campaignApi: CampaignApi) : CampaignRepository {

    private fun decodeContent(content: String?): Content? {
        return if (content != null) {
            serializer.decodeFromString(Content.serializer(), content)
        } else {
            null
        }
    }

    private fun Campaign.Network.asOutput(): Campaign.Output = Campaign.Output(
        content = decodeContent(content),
        type = type,
        sequencedCampaign = sequencedCampaign?.asOutput(),
        childrenCampaigns = childrenCampaigns?.map { it.asOutput() }
    )

    override suspend fun get(userId: String, type: Campaign.Type): Campaign.Output? {
        return when (val response = campaignApi.get(userId, type)) {
            is RequestResult.Exception -> {
                null
            }

            is RequestResult.Success -> {
                try {

                    val output = response.data.asOutput()
                    println(output)
                    output
                } catch (error: Throwable) {
                    println("ERror converting")
                    println(error.message)
                    null
                }
            }
        }
    }
}