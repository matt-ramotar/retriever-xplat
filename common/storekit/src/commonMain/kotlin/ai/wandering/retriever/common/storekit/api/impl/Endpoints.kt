package ai.wandering.retriever.common.storekit.api.impl

object Endpoints {
    private const val ROOT = "https://www.api.retriever.wandering.ai"

    const val CHANNEL = "$ROOT/channels"
    const val DEMO_SIGN_IN = "$ROOT/auth/demo"
    const val GOOGLE_ONE_TAP_SIGN_IN = "$ROOT/auth/google"
    const val VALIDATE_TOKEN = "$ROOT/auth/token"
}