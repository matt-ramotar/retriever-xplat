package ai.wandering.retriever.common.storekit.repository.auth

import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser

interface AuthRepository {
    suspend fun signIn(): AuthenticatedUser
}