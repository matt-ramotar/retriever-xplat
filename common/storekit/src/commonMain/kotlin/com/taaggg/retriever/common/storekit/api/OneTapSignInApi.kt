package com.taaggg.retriever.common.storekit.api

import com.taaggg.retriever.common.storekit.entities.auth.GoogleAuthResponse
import com.taaggg.retriever.common.storekit.entities.auth.GoogleUser
import com.taaggg.retriever.common.storekit.result.RequestResult

interface OneTapSignInApi {
    suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse>
}