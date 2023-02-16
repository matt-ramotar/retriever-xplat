package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entities.UserNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

interface Socket {
    fun emit(event: String, payload: JsonObject)
    fun emit(event: String, payload: JsonArray)
    fun emit(event: String, payload: String)
    fun connect()
    fun disconnect()
    fun isConnected(): Boolean
    fun subscribeToNotifications(userId: String): Flow<List<UserNotification>>
}


fun NotificationsInput.toJsonObject(): JsonObject = toJsonObject()

@Serializable
data class NotificationsInput(
    val userId: String
)