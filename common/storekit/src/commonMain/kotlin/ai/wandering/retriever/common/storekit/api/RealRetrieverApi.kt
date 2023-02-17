package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.notifications.Notification
import ai.wandering.retriever.common.notifications.Notifications
import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.entities.auth.GoogleAuthResponse
import ai.wandering.retriever.common.storekit.entities.auth.GoogleUser
import ai.wandering.retriever.common.storekit.entities.user.network.NetworkUser
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@Serializable
data class ValidateTokenInput(
    val token: String
)


class RealRetrieverApi(private val client: HttpClient, private val socket: Socket) : RetrieverApi {
    override suspend fun validateToken(token: String): RequestResult<NetworkUser> = try {
        val response = client.post("$ROOT_API_URL/auth/token") {
            setBody(ValidateTokenInput(token))
            contentType(ContentType.Application.Json)
        }
        RequestResult.Success(response.body())
    } catch (error: Error) {
        RequestResult.Exception(error)
    }

    override suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse> = try {
        val response = client.post("$ROOT_API_URL/auth/google") {
            setBody(googleUser)
            contentType(ContentType.Application.Json)
        }
        val googleAuthResponse = response.body<GoogleAuthResponse>()
        RequestResult.Success(googleAuthResponse)
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    override suspend fun login(): RequestResult<NetworkUser> = try {
        val response = client.get("$ROOT_API_URL/auth/demo")
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        println("ERROR = $error")
        RequestResult.Exception(error)
    }

    override fun subscribeToNotifications(userId: String): SharedFlow<List<Notification>> = MutableSharedFlow<List<Notification>>().also {
        socket.on("notifications") { response ->
            try {
                val serializer = Json { ignoreUnknownKeys = true }

                val notificationsJson = response.firstOrNull().toString()
                val notifications = serializer.decodeFromString<Notifications>(notificationsJson)
                it.emit(notifications.notifications)
            } catch (error: Exception) {
                println(error)
            }
        }

        socket.emit("notifications", userId)
    }

    companion object {
        private const val ROOT_API_URL = "https://www.api.retriever.wandering.ai"
    }
}