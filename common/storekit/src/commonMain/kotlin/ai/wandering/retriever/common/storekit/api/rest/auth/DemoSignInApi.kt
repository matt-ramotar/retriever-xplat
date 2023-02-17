package ai.wandering.retriever.common.storekit.api.rest.auth

import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.result.RequestResult

interface DemoSignInApi {
    suspend fun signIn(): RequestResult<User.Network>
}