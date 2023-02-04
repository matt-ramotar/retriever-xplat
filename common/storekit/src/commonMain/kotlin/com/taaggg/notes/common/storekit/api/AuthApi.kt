package com.taaggg.notes.common.storekit.api

import com.taaggg.notes.common.storekit.entities.user.network.NetworkUser
import com.taaggg.notes.common.storekit.result.RequestResult

interface AuthApi : OneTapSignInApi {
    suspend fun validateToken(token: String): RequestResult<NetworkUser>
}