package ai.wandering.retriever.common.storekit.api.impl.auth

import ai.wandering.retriever.common.storekit.api.auth.DemoSignInApi
import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RealDemoSignInApi(private val client: HttpClient) : DemoSignInApi {
    override suspend fun signIn(): RequestResult<User.Network> = try {
        val response = client.get(Endpoints.DEMO_SIGN_IN)
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}