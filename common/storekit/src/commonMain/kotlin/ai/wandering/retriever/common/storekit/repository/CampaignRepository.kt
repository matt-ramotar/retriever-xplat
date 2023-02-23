package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.Campaign

interface CampaignRepository {
    suspend fun get(userId: String, type: Campaign.Type): Campaign.Output?
}