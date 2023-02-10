package com.taaggg.retriever.common.storekit.repository

import com.taaggg.retriever.common.storekit.api.AuthApi
import com.taaggg.retriever.common.storekit.entities.auth.GoogleAuthResponse
import com.taaggg.retriever.common.storekit.entities.auth.GoogleUser
import com.taaggg.retriever.common.storekit.entities.user.output.User
import com.taaggg.retriever.common.storekit.extension.toUser
import com.taaggg.retriever.common.storekit.result.RequestResult

class RealAuthRepository(private val api: AuthApi) : AuthRepository {
    override suspend fun validateToken(token: String): RequestResult<User> = when (val response = api.validateToken(token)) {
        is RequestResult.Exception -> response
        is RequestResult.Success -> RequestResult.Success(response.data.toUser())
    }

    override suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse> = api.google(googleUser)
}