package ai.wandering.retriever.common.storekit.entities.user.local

data class LocalUser(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null,
)