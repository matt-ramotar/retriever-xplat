package ai.wandering.retriever.common.storekit.entities.user.network

import ai.wandering.retriever.common.storekit.entities.user.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser(
    @SerialName("_id")
    val id: UserId,
    val username: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null
)