package ai.wandering.retriever.common.storekit.entity.auth

import ai.wandering.retriever.common.storekit.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthResponse(
    val user: User.Network,
    val token: String
)