package com.taaggg.retriever.android.app.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taaggg.retriever.android.app.auth.model.state.DemoSignInViewState
import com.taaggg.retriever.android.app.auth.model.state.LoginViewState
import com.taaggg.retriever.android.app.auth.model.viewmodel.DemoSignInViewModel
import com.taaggg.retriever.android.common.sig.R
import com.taaggg.retriever.android.common.sig.color.Sig
import com.taaggg.retriever.android.common.sig.color.systemThemeColors
import com.taaggg.retriever.common.storekit.entities.user.output.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewState: LoginViewState,
    demoSignInViewModel: DemoSignInViewModel = viewModel(),
    signIn: () -> Unit,
    handleToken: (User) -> Unit,
    startMainActivity: (User) -> Unit
) {
    val demoSignInViewState = demoSignInViewModel.state.collectAsState()
    val isSuccess = remember { mutableStateOf(false) }

    val colors = systemThemeColors()

    when (viewState) {
        LoginViewState.NoToken.WaitingForUserToSubmit -> {

            fun demoSignIn() {
                demoSignInViewModel.login()
                when (val response = demoSignInViewState.value.viewState) {
                    DemoSignInViewState.Failure -> {}
                    DemoSignInViewState.Initial -> {}
                    DemoSignInViewState.Loading -> {}
                    is DemoSignInViewState.Success -> {
                        startMainActivity(response.user)
                    }
                }

            }

            LaunchedEffect(true) {
                demoSignIn()
            }

            Scaffold(topBar = {}) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .background(Sig.ColorScheme.background), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                ) {

                    if (isSuccess.value) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(colors.green.background)
                        ) {
                            Text(text = "Success!", color = colors.green.text)
                        }
                    }

                    Image(painter = painterResource(id = R.drawable.retriever), contentDescription = null, modifier = Modifier.size(150.dp))

                    Spacer(modifier = Modifier.size(48.dp))

                    DemoSignInButton {
                        demoSignIn()
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    GoogleSignInButton {
                        signIn()
                    }
                }
            }
        }

        is LoginViewState.Token.Data -> {
            LaunchedEffect(viewState.token) {
                handleToken(viewState.user)
            }
        }

        else -> {
            // TODO(mramotar)
        }
    }
}