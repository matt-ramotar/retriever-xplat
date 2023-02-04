package com.taaggg.notes.common.storekit.repository

import com.taaggg.notes.common.storekit.entities.user.output.User
import com.taaggg.notes.common.storekit.result.RequestResult

interface AuthRepository : OneTapSignInRepository {
    suspend fun validateToken(token: String): RequestResult<User>
}


