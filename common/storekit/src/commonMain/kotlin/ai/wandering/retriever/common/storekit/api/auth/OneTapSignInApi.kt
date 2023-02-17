package ai.wandering.retriever.common.storekit.api.auth

import ai.wandering.retriever.common.storekit.entities.auth.GoogleAuthResponse
import ai.wandering.retriever.common.storekit.entities.auth.GoogleUser
import ai.wandering.retriever.common.storekit.result.RequestResult

interface OneTapSignInApi {
    suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse>
}