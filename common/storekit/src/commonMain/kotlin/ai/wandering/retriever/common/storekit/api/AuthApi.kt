package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entities.user.network.NetworkUser
import ai.wandering.retriever.common.storekit.result.RequestResult

interface AuthApi : OneTapSignInApi, DemoApi {
    suspend fun validateToken(token: String): RequestResult<NetworkUser>
}