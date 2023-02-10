package com.taaggg.retriever.android.app.auth.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taaggg.retriever.android.app.auth.model.state.LoginViewState
import com.taaggg.retriever.android.common.sig.R
import com.taaggg.retriever.common.storekit.entities.user.output.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewState: LoginViewState, signIn: () -> Unit, handleToken: (User) -> Unit) {
    when (viewState) {
        LoginViewState.NoToken.WaitingForUserToSubmit -> {
            Scaffold(topBar = {}) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(Color.Black), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                ) {

                    Image(painter = painterResource(id = R.drawable.dropbox), contentDescription = null, modifier = Modifier.size(150.dp))

                    Spacer(modifier = Modifier.size(48.dp))

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