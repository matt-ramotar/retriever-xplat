package ai.wandering.retriever.common.storekit.api.impl.auth

import ai.wandering.retriever.common.storekit.api.rest.auth.AuthApi
import ai.wandering.retriever.common.storekit.api.rest.auth.DemoSignInApi
import ai.wandering.retriever.common.storekit.api.rest.auth.OneTapSignInApi
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.entity.auth.ValidateTokenRequest
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RealAuthApi(private val client: HttpClient, oneTapSignInApi: OneTapSignInApi, demoSignInApi: DemoSignInApi) :
    AuthApi,
    DemoSignInApi by demoSignInApi,
    OneTapSignInApi by oneTapSignInApi {

    override suspend fun validateToken(userId: String, token: String): RequestResult<User.Network> = try {
        val response = client.post(Endpoints.VALIDATE_TOKEN) {
            setBody(ValidateTokenRequest(userId, token))
            contentType(ContentType.Application.Json)
        }
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}