package ai.wandering.retriever.common.storekit.entity.auth

data class ValidateTokenRequest(
    val userId: String,
    val token: String
)