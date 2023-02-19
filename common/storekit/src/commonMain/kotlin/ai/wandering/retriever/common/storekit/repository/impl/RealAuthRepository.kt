package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.api.rest.auth.AuthApi
import ai.wandering.retriever.common.storekit.converter.asUnpopulatedOutput
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.repository.auth.AuthRepository
import ai.wandering.retriever.common.storekit.result.RequestResult

class RealAuthRepository(private val authApi: AuthApi) : AuthRepository {
    override suspend fun signIn(): AuthenticatedUser {
        val network = authApi.signIn()
        println("Network: $network")
        require(network is RequestResult.Success)
        return network.data.asUnpopulatedOutput()
    }
}