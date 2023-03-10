package ai.wandering.retriever.common.storekit.entity.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleUser(
    val name: String? = null,
    val email: String,
    val avatarUrl: String? = null
)