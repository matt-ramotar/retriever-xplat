package ai.wandering.retriever.common.storekit.entities.auth

import ai.wandering.retriever.common.storekit.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthResponse(
    val user: User.Network,
    val token: String
)