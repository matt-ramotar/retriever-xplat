package ai.wandering.retriever.common.storekit.api.impl.auth

import ai.wandering.retriever.common.storekit.api.impl.Endpoints
import ai.wandering.retriever.common.storekit.api.rest.auth.DemoSignInApi
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class RealDemoSignInApi(private val serializer: Json, private val client: HttpClient) : DemoSignInApi {
    override suspend fun signIn(): RequestResult<User.Network> = try {
        val response = client.get(Endpoints.DEMO_SIGN_IN)
        val network = serializer.decodeFromString(User.Network.serializer(), response.bodyAsText())
        RequestResult.Success(network)
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}