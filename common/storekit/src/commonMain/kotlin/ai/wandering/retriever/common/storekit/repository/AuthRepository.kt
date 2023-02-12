package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entities.user.output.User
import ai.wandering.retriever.common.storekit.result.RequestResult

interface AuthRepository : OneTapSignInRepository, DemoRepository {
    suspend fun validateToken(token: String): RequestResult<User>
}


