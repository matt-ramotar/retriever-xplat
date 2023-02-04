package com.taaggg.notes.android.app.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.taaggg.notes.android.app.MainActivity
import com.taaggg.notes.android.app.NotesApp
import com.taaggg.notes.android.app.R
import com.taaggg.notes.android.app.appDependencies
import com.taaggg.notes.android.app.auth.model.viewmodel.LoginViewModel
import com.taaggg.notes.android.app.auth.ui.LoginScreen
import com.taaggg.notes.android.app.notesApp
import com.taaggg.notes.android.app.userComponentFactory
import com.taaggg.notes.android.app.wiring.AppComponent
import com.taaggg.notes.android.app.wiring.AppDependencies
import com.taaggg.notes.android.app.wiring.UserComponent
import com.taaggg.notes.common.storekit.api.NotesApi
import com.taaggg.notes.common.storekit.entities.auth.GoogleUser
import com.taaggg.notes.common.storekit.entities.user.output.User
import com.taaggg.notes.common.storekit.extension.toUser
import com.taaggg.notes.common.storekit.result.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOKEN_KEY = "TOKEN KEY"
const val AUTH_DATA_STORE = "AUTH DATA STORE"
val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATA_STORE)

class LoginActivity : ComponentActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val appComponent: AppComponent by lazy { notesApp().component }

    private lateinit var signInClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val userComponentFactory: UserComponent.Factory by lazy { appComponent.userComponentFactory() }
    private val viewModel = viewModels<LoginViewModel> {
        val appComponent = (application as NotesApp).component
        val appDependencies = appComponent as AppDependencies
        val authRepository = appDependencies.authRepository
        val authDataStore = applicationContext.authDataStore
        ViewModelProvider.Factory.from(
            ViewModelInitializer(LoginViewModel::class.java) {
                LoginViewModel(authRepository, authDataStore)
            }
        )
    }

    private val api: NotesApi by lazy {
        appComponent.appDependencies().api
    }

    private fun createUserComponent(user: User) {
        userComponentFactory.create(user)
    }

    private fun startMainActivity(user: User) {
        val intent = MainActivity.getLaunchIntent(applicationContext, user.parcelize())
        startActivity(intent)
    }

    private fun handleToken(user: User) {
        createUserComponent(user)
        startMainActivity(user)
    }

    private fun signIn() {
        signInClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->

                val intent = result.pendingIntent.intentSender
                val requestCode = REQUEST_CODE
                val fillInIntent = null
                val flagsMask = 0
                val flagsValue = 0
                val extraFlags = 0
                val options = null

                startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValue, extraFlags, options)
            }
            .addOnFailureListener(this) { error ->
                // TODO(mramotar): Display error message
                println("Error: $error")
            }
    }

    private suspend fun continueWithGoogle(user: GoogleUser) {
        when (val response = api.google(user)) {
            is RequestResult.Exception -> TODO()
            is RequestResult.Success -> {
                viewModel.value.setToken(response.data.token)
                createUserComponent(response.data.user.toUser())
                startMainActivity(response.data.user.toUser())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE -> {
                try {
                    val credential = signInClient.getSignInCredentialFromIntent(data)
                    when {
                        credential.googleIdToken != null -> {
                            val name = credential.displayName
                            val email = credential.id
                            val avatarUrl = credential.profilePictureUri

                            val googleUser = GoogleUser(name, email, avatarUrl?.toString())

                            coroutineScope.launch {
                                continueWithGoogle(googleUser)
                            }
                        }

                        credential.password != null -> {
                            // TODO(mramotar): Handle normal login
                        }

                        else -> {}
                    }
                } catch (error: ApiException) {
                    // TODO(mramotar): Display error message
                    println("Error: $error")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInClient = signInClient()
        signInRequest = signInRequest()

        setContent {
            val viewState = viewModel.value.state.collectAsState().value.viewState
            LoginScreen(viewState, ::signIn, ::handleToken)
        }
    }

    private fun signInClient() = Identity.getSignInClient(this)
    private fun signInRequest() = BeginSignInRequest.builder()
        .setPasswordRequestOptions(
            BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build()
        )
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(getString(R.string.server_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(false)
        .build()

    companion object {
        private const val REQUEST_CODE = 1
    }
}