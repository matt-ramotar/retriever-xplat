package com.taaggg.retriever.common.storekit.repository

import com.taaggg.retriever.common.storekit.entities.user.output.User
import com.taaggg.retriever.common.storekit.result.RequestResult

interface AuthRepository : OneTapSignInRepository, DemoRepository {
    suspend fun validateToken(token: String): RequestResult<User>
}


