package ai.wandering.retriever.common.storekit.entities.auth

data class ValidateTokenRequest(
    val userId: String,
    val token: String
)