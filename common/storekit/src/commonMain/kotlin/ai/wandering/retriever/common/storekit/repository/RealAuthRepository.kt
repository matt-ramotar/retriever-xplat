package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.api.auth.AuthApi
import ai.wandering.retriever.common.storekit.entities.auth.GoogleAuthResponse
import ai.wandering.retriever.common.storekit.entities.auth.GoogleUser
import ai.wandering.retriever.common.storekit.entities.user.network.NetworkUser
import ai.wandering.retriever.common.storekit.entities.user.output.User
import ai.wandering.retriever.common.storekit.extension.toUser
import ai.wandering.retriever.common.storekit.result.RequestResult

class RealAuthRepository(private val api: AuthApi) : AuthRepository {
    override suspend fun validateToken(token: String): RequestResult<User> = when (val response = api.validateToken(token)) {
        is RequestResult.Exception -> response
        is RequestResult.Success -> RequestResult.Success(response.data.toUser())
    }

    override suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse> = api.google(googleUser)
    override suspend fun login(): RequestResult<NetworkUser> = api.signIn()
}