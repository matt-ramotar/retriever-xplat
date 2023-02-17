package ai.wandering.retriever.common.storekit.api.rest.auth

import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.result.RequestResult

interface AuthApi : OneTapSignInApi, DemoSignInApi {
    suspend fun validateToken(userId: String, token: String): RequestResult<User.Network>
}