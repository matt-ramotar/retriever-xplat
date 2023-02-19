package ai.wandering.retriever.android.app.auth.model.state

import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser

sealed class DemoSignInViewState {
    object Initial : DemoSignInViewState()
    object Loading : DemoSignInViewState()
    object Failure : DemoSignInViewState()
    data class Success(val user: AuthenticatedUser) : DemoSignInViewState()
}
