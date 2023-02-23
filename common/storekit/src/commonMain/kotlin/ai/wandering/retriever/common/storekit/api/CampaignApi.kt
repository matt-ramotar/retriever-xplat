package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entity.Campaign
import ai.wandering.retriever.common.storekit.result.RequestResult

interface CampaignApi {
    suspend fun get(userId: String, type: Campaign.Type): RequestResult<Campaign.Network>
}