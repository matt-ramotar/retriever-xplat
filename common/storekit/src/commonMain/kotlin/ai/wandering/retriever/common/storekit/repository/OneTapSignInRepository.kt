package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entities.auth.GoogleAuthResponse
import ai.wandering.retriever.common.storekit.entities.auth.GoogleUser
import ai.wandering.retriever.common.storekit.result.RequestResult

interface OneTapSignInRepository {
    suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse>
}