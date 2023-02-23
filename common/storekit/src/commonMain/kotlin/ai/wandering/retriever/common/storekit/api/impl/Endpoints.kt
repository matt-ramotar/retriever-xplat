package ai.wandering.retriever.common.storekit.api.impl

import ai.wandering.retriever.common.storekit.entity.Campaign

object Endpoints {
    private const val ROOT = "https://www.api.retriever.wandering.ai"

    const val SOCKET = ROOT
    const val USER = "$ROOT/users"

    const val CHANNEL = "$ROOT/channels"
    const val DEMO_SIGN_IN = "$ROOT/auth/demo"
    const val GOOGLE_ONE_TAP_SIGN_IN = "$ROOT/auth/google"
    const val PAGING_NOTE = "$ROOT/paging/notes"
    const val PAGING_USER_ACTION = "$ROOT/paging/user_action"
    const val VALIDATE_TOKEN = "$ROOT/auth/token"

    fun single(id: String, collection: Collection, populate: Boolean = false) = "$ROOT/${collection.value}/$id?populate=$populate"
    fun upsert(collection: Collection) = "$ROOT/${collection.value}"
    fun collection(userId: String, collection: Collection, populate: Boolean = false) = "$USER/$userId/${collection.value}?populate=$populate"
    fun paging(userId: String, collection: Collection) = "$USER/$userId/paging/${collection.value}"
    fun campaign(userId: String, campaignType: Campaign.Type) = "$USER/$userId/campaigns/${campaignType.value}"
}