package ai.wandering.retriever.common.storekit.api

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
import kotlinx.serialization.Serializable


@Serializable
data class ValidateTokenInput(
    val token: String
)


class RealRetrieverApi(private val client: HttpClient) : RetrieverApi {
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

    companion object {
        private const val ROOT_API_URL = "https://www.api.retriever.wandering.ai"
    }
}