package com.taaggg.notes.common.storekit.repository

import com.taaggg.notes.common.storekit.entities.auth.GoogleAuthResponse
import com.taaggg.notes.common.storekit.entities.auth.GoogleUser
import com.taaggg.notes.common.storekit.result.RequestResult

interface OneTapSignInRepository {
    suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse>
}