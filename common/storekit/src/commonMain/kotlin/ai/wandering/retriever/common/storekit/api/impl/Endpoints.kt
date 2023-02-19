package ai.wandering.retriever.common.storekit.api.impl

object Endpoints {
    private const val ROOT = "https://www.api.retriever.wandering.ai"

    const val SOCKET = ROOT
    const val USER = "$ROOT/users"

    const val CHANNEL = "$ROOT/channels"
    const val DEMO_SIGN_IN = "$ROOT/auth/demo"
    const val GOOGLE_ONE_TAP_SIGN_IN = "$ROOT/auth/google"
    const val PAGING_NOTE = "$ROOT/paging/notes"
    const val PAGING_USER_ACTION = "$ROOT/paging/user_action"
    const val VALIDATE_TOKEN = "$ROOT/auth/token"

    fun generate(userId: String, collection: Collection) = "$USER/$userId/${collection.value}"
}