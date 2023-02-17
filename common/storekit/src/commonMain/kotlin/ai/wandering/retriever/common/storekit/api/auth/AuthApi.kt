package ai.wandering.retriever.common.storekit.api.auth

import ai.wandering.retriever.common.storekit.entities.User
import ai.wandering.retriever.common.storekit.result.RequestResult

interface AuthApi : OneTapSignInApi, DemoSignInApi {
    suspend fun validateToken(userId: String, token: String): RequestResult<User.Network>
}