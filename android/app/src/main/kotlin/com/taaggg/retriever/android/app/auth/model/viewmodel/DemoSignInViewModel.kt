package com.taaggg.retriever.android.app.auth.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taaggg.retriever.android.app.auth.model.state.DemoSignInState
import com.taaggg.retriever.android.app.auth.model.state.DemoSignInViewState
import com.taaggg.retriever.common.storekit.extension.toUser
import com.taaggg.retriever.common.storekit.repository.AuthRepository
import com.taaggg.retriever.common.storekit.result.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DemoSignInViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val stateFlow = MutableStateFlow(DemoSignInState(DemoSignInViewState.Initial))
    val state: StateFlow<DemoSignInState> = stateFlow
    fun login() {
        setState(DemoSignInState(DemoSignInViewState.Loading))
        viewModelScope.launch {
            when (val response = authRepository.login()) {
                is RequestResult.Exception -> setState(DemoSignInState(DemoSignInViewState.Failure))
                is RequestResult.Success -> setState(DemoSignInState(DemoSignInViewState.Success(response.data.toUser())))
            }
        }
    }

    private fun setState(state: DemoSignInState) {
        stateFlow.value = state
    }

}