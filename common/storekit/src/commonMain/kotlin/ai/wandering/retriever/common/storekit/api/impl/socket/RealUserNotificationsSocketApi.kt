package ai.wandering.retriever.common.storekit.api.impl.socket

import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.api.impl.SocketEvents
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.entity.UserNotifications
import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RealUserNotificationsSocketApi(private val serializer: Json, private val socket: Socket) : UserNotificationsSocketApi {
    override fun subscribe(id: String): Flow<RequestResult<UserNotifications>> = MutableSharedFlow<RequestResult<UserNotifications>>().also { sharedFlow ->
        socket.on(SocketEvents.NOTIFICATIONS) { response ->
            try {
                val notificationsJson = response.firstOrNull()?.toString()
                if (notificationsJson != null) {
                    val notifications = serializer.decodeFromString<UserNotifications>(notificationsJson)
                    sharedFlow.emit(RequestResult.Success(notifications))
                } else {
                    throw Exception("Empty")
                }
            } catch (error: Exception) {
                sharedFlow.emit(RequestResult.Exception(error))
            }
        }

        socket.emit(SocketEvents.NOTIFICATIONS, id)

    }
}