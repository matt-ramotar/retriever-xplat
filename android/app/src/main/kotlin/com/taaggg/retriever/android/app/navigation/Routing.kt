package com.taaggg.retriever.android.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.taaggg.retriever.android.app.MainActivity
import com.taaggg.retriever.android.app.RetrieverApp
import com.taaggg.retriever.android.app.wiring.AppDependencies
import com.taaggg.retriever.android.common.navigation.Screen
import com.taaggg.retriever.android.common.scoping.UserDependencies

@Composable
fun Routing(navController: NavHostController, innerPadding: PaddingValues) {

    val app = LocalContext.current.applicationContext as RetrieverApp
    val mainActivity = LocalContext.current as MainActivity

    val appComponent = app.component
    val userComponent = mainActivity.component
    val appDependencies = appComponent as AppDependencies
    val userDependencies = userComponent as UserDependencies

    NavHost(navController = navController, startDestination = Screen.Account.route, modifier = Modifier.padding(innerPadding)) {
        composable(Screen.Home.route) {
            // TODO(mramotar)
        }

        composable(Screen.Account.route) {
            // TODO(mramotar)
        }
    }
}