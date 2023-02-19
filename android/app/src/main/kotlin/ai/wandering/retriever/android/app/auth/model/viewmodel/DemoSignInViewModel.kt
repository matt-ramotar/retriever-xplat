package ai.wandering.retriever.android.app.auth.model.viewmodel

import ai.wandering.retriever.android.app.auth.model.state.DemoSignInState
import ai.wandering.retriever.android.app.auth.model.state.DemoSignInViewState
import ai.wandering.retriever.common.storekit.repository.auth.AuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DemoSignInViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val stateFlow = MutableStateFlow(DemoSignInState(DemoSignInViewState.Initial))
    val state: StateFlow<DemoSignInState> = stateFlow

    fun signIn() {
        setState(DemoSignInState(DemoSignInViewState.Loading))
        viewModelScope.launch {
            try {
                val user = authRepository.signIn()
                println("User: $user")
                setState(DemoSignInState(DemoSignInViewState.Success(user)))
            } catch (error: Throwable) {
                println("Error: $error")
                setState(DemoSignInState(DemoSignInViewState.Failure))
            }
        }
    }

    private fun setState(state: DemoSignInState) {
        stateFlow.value = state
    }
}