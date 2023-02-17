package ai.wandering.retriever.common.storekit.api.rest.auth

import ai.wandering.retriever.common.storekit.entity.auth.GoogleAuthResponse
import ai.wandering.retriever.common.storekit.entity.auth.GoogleUser
import ai.wandering.retriever.common.storekit.result.RequestResult

interface OneTapSignInApi {
    suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse>
}