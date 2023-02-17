package ai.wandering.retriever.common.storekit.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject


fun NotificationsInput.toJsonObject(): JsonObject = toJsonObject()

@Serializable
data class NotificationsInput(
    val userId: String
)