package ai.wandering.retriever.common.storekit.api.auth

import ai.wandering.retriever.common.storekit.entities.User
import ai.wandering.retriever.common.storekit.result.RequestResult

interface DemoSignInApi {
    suspend fun signIn(): RequestResult<User.Network>
}