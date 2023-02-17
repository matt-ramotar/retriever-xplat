package ai.wandering.retriever.android.app.auth.model.state

import ai.wandering.retriever.common.storekit.entity.user.output.User

sealed class LoginViewState {
    object Initial : LoginViewState()

    sealed class Token : LoginViewState() {
        object Loading : Token()
        data class Data(
            val user: User,
            val token: String,
        ) : Token()

        data class Error(
            val exception: Throwable
        ) : Token()
    }

    sealed class NoToken : LoginViewState() {
        object WaitingForUserToSubmit : NoToken()
        object Syncing : NoToken()
        data class Data(
            val user: User,
            val token: String,
        ) : NoToken()

        data class Error(
            val exception: Throwable
        ) : Token()
    }
}