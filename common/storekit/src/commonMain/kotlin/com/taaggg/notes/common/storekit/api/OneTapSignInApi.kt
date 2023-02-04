package com.taaggg.notes.common.storekit.api

import com.taaggg.notes.common.storekit.entities.auth.GoogleAuthResponse
import com.taaggg.notes.common.storekit.entities.auth.GoogleUser
import com.taaggg.notes.common.storekit.result.RequestResult

interface OneTapSignInApi {
    suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse>
}