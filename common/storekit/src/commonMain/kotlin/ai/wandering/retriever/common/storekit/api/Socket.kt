package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entities.UserNotification
import dev.icerock.moko.socket.SocketEvent
import dev.icerock.moko.socket.SocketOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import dev.icerock.moko.socket.Socket as Io

interface Socket {
    fun emit(event: String, payload: JsonObject)
    fun emit(event: String, payload: JsonArray)
    fun emit(event: String, payload: String)
    fun connect()
    fun disconnect()
    fun isConnected(): Boolean
    fun subscribeToNotifications(userId: String): Flow<List<UserNotification>>
}

expect class RetrieverSocket : Socket

class SocketManager {
    private val _notifications: MutableStateFlow<List<UserNotification>> = MutableStateFlow(listOf())
    val notifications: StateFlow<List<UserNotification>> = _notifications

    val socket = Io(
        endpoint = "https://www.api.retriever.wandering.ai",
        config = SocketOptions(
            queryParams = null,
            transport = SocketOptions.Transport.WEBSOCKET
        )
    ) {
        on(SocketEvent.Connect) {
            println("connect")
        }

        on(SocketEvent.Connecting) {
            println("connecting")
            println(this)
        }

        on(SocketEvent.Disconnect) {
            println("disconnect")
        }

        on(SocketEvent.Error) {
            println("error $it")
            println(it.message)
            println("${it.cause}")
        }

        on(SocketEvent.Reconnect) {
            println("reconnect")
        }

        on(SocketEvent.ReconnectAttempt) {
            println("reconnect attempt $it")
        }

        on(SocketEvent.Ping) {
            println("ping")
        }

        on(SocketEvent.Pong) {
            println("pong")
        }

//        on("notifications") { response ->
//            val notifications: List<UserNotification> = Json.decodeFromString(response)
//            _notifications.value = notifications
//        }
    }

}


fun NotificationsInput.toJsonObject(): JsonObject = toJsonObject()

@Serializable
data class NotificationsInput(
    val userId: String
)