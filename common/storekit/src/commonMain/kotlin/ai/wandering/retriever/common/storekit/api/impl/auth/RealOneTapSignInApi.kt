package ai.wandering.retriever.common.storekit.api.impl.auth

import ai.wandering.retriever.common.storekit.api.auth.OneTapSignInApi
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.entity.auth.GoogleAuthResponse
import ai.wandering.retriever.common.storekit.entity.auth.GoogleUser
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RealOneTapSignInApi(private val client: HttpClient) : OneTapSignInApi {
    override suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse> = try {
        val response = client.post(Endpoints.GOOGLE_ONE_TAP_SIGN_IN) {
            setBody(googleUser)
            contentType(ContentType.Application.Json)
        }
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}