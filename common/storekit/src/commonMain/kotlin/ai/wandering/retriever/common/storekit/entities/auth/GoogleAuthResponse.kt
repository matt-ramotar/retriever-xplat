package ai.wandering.retriever.common.storekit.entities.auth

import ai.wandering.retriever.common.storekit.entities.user.network.NetworkUser
import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthResponse(
    val user: NetworkUser,
    val token: String
)