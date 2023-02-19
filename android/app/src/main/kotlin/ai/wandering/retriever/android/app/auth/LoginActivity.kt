@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.app.auth

import ai.wandering.retriever.android.app.MainActivity
import ai.wandering.retriever.android.app.auth.model.state.DemoSignInViewState
import ai.wandering.retriever.android.app.auth.model.viewmodel.DemoSignInViewModel
import ai.wandering.retriever.android.app.auth.ui.DemoSignInButton
import ai.wandering.retriever.android.app.datastore.userDataStore
import ai.wandering.retriever.android.app.notesApp
import ai.wandering.retriever.android.app.wiring.AppComponent
import ai.wandering.retriever.android.app.wiring.AppDependencies
import ai.wandering.retriever.android.common.sig.R
import ai.wandering.retriever.android.common.sig.SigTheme
import ai.wandering.retriever.android.common.sig.color.Sig
import ai.wandering.retriever.android.common.sig.color.systemThemeColors
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.repository.auth.AuthRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import kotlinx.serialization.json.Json


class LoginActivity : ComponentActivity() {

    private val userDataStore by lazy { applicationContext.userDataStore }

    private val appComponent: AppComponent by lazy { notesApp().component }

    private val appDependencies: AppDependencies by lazy { appComponent as AppDependencies }
    private val serializer: Json by lazy { appDependencies.serializer }
    private val authRepository: AuthRepository by lazy { appDependencies.authRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val colors = systemThemeColors()
            val demoSignInState = demoSignInViewModel.value.state.collectAsState()

            SigTheme {
                when (val viewState = demoSignInState.value.viewState) {
                    DemoSignInViewState.Initial,
                    DemoSignInViewState.Loading -> {
                        Scaffold(topBar = {}) { paddingValues ->
                            Column(
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp)
                                    .background(Sig.ColorScheme.background), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                            ) {

                                Image(painter = painterResource(id = R.drawable.retriever), contentDescription = null, modifier = Modifier.size(150.dp))

                                Spacer(modifier = Modifier.size(48.dp))

                                DemoSignInButton { demoSignInViewModel.value.signIn() }
                            }
                        }
                    }

                    DemoSignInViewState.Failure -> {
                        Text(text = "Failure")
                    }

                    is DemoSignInViewState.Success -> {
                        LaunchedEffect(viewState.user.id) {

                            val key = stringPreferencesKey(viewState.user.id)
                            val value = serializer.encodeToString(User.Output.Unpopulated.serializer(), viewState.user)
                            userDataStore.edit { it[key] = value }
                            startMainActivity(viewState.user)
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colors.standard.background)
                        ) {

                        }

                    }
                }

            }
        }
    }

    private val demoSignInViewModel = viewModels<DemoSignInViewModel> {
        ViewModelProvider.Factory.from(
            ViewModelInitializer(DemoSignInViewModel::class.java) {
                DemoSignInViewModel(authRepository)
            }
        )
    }

    private fun startMainActivity(user: AuthenticatedUser) {
        val intent = MainActivity.getLaunchIntent(applicationContext, user.id)
        startActivity(intent)
    }
}