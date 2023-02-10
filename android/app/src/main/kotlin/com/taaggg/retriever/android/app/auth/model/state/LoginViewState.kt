package com.taaggg.retriever.android.app.auth.model.state

import com.taaggg.retriever.common.storekit.entities.user.output.User

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