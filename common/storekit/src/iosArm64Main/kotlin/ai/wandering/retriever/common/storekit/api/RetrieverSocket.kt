package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entities.UserNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject


actual class RetrieverSocket : Socket {
    private val socketManager = SocketManager()
    private val socket = socketManager.socket
    override fun emit(event: String, payload: JsonObject) = socket.emit(event, payload)

    override fun emit(event: String, payload: JsonArray) = socket.emit(event, payload)

    override fun emit(event: String, payload: String) = socket.emit(event, payload)

    override fun connect() = socket.connect()

    override fun disconnect() = socket.disconnect()

    override fun isConnected(): Boolean = socket.isConnected()

    override fun subscribeToNotifications(userId: String): Flow<List<UserNotification>> = channelFlow {
        emit("notifications", NotificationsInput(userId).toJsonObject())

        socketManager.notifications.collectLatest {
            trySend(it)
        }
    }
}