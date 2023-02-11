package com.taaggg.retriever.android.app.auth.model.state

import com.taaggg.retriever.common.storekit.entities.user.output.User

sealed class DemoSignInViewState {
    object Initial : DemoSignInViewState()
    object Loading : DemoSignInViewState()
    object Failure : DemoSignInViewState()
    data class Success(val user: User) : DemoSignInViewState()
}
