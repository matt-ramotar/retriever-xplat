package ai.wandering.retriever.android.app.auth.model.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ai.wandering.retriever.android.app.auth.TOKEN_KEY
import ai.wandering.retriever.android.app.auth.extension.tokenOrNull
import ai.wandering.retriever.android.app.auth.model.state.LoginState
import ai.wandering.retriever.android.app.auth.model.state.LoginViewState
import ai.wandering.retriever.common.storekit.repository.AuthRepository
import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ValidateTokenViewModel(
    private val authRepository: AuthRepository,
    private val authDataStore: DataStore<Preferences>
) : ViewModel() {
    private val stateFlow = MutableStateFlow(LoginState(LoginViewState.Initial))
    val state: StateFlow<LoginState> = stateFlow

    init {
        viewModelScope.launch { tryLoadAndValidateToken() }
    }

    /**
     * Try to load token from data store. If token, then try to load user. Else, clear token.
     */
    private suspend fun tryLoadAndValidateToken() {
        val token = authDataStore.tokenOrNull()

        if (token != null) {
            setState(LoginState(LoginViewState.Token.Loading))
            try {
                when (val response = authRepository.validateToken(token)) {
                    is RequestResult.Exception -> handleFailure(response.error)
                    is RequestResult.Success -> setState(LoginState(LoginViewState.Token.Data(response.data, token)))
                }
            } catch (error: Throwable) {
                handleFailure(error)
            }
        } else {
            setState(LoginState(LoginViewState.NoToken.WaitingForUserToSubmit))
        }
    }
    private fun setState(state: LoginState) {
        stateFlow.value = state
    }

    private fun withState(block: (LoginState) -> Unit) = block(stateFlow.value)

    private suspend fun handleFailure(error: Throwable) {
        setState(LoginState(LoginViewState.Token.Error(error)))
        authDataStore.edit { preferences -> preferences.remove(stringPreferencesKey(TOKEN_KEY)) }
        setState(LoginState(LoginViewState.NoToken.WaitingForUserToSubmit))
    }

    internal suspend fun setToken(token: String) = authDataStore.edit {
        it[stringPreferencesKey(TOKEN_KEY)] = token
    }
}

