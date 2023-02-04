package com.taaggg.notes.common.storekit.repository

import com.taaggg.notes.common.storekit.api.AuthApi
import com.taaggg.notes.common.storekit.entities.auth.GoogleAuthResponse
import com.taaggg.notes.common.storekit.entities.auth.GoogleUser
import com.taaggg.notes.common.storekit.entities.user.output.User
import com.taaggg.notes.common.storekit.extension.toUser
import com.taaggg.notes.common.storekit.result.RequestResult

class RealAuthRepository(private val api: AuthApi) : AuthRepository {
    override suspend fun validateToken(token: String): RequestResult<User> = when (val response = api.validateToken(token)) {
        is RequestResult.Exception -> response
        is RequestResult.Success -> RequestResult.Success(response.data.toUser())
    }

    override suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse> = api.google(googleUser)
}