package com.taaggg.notes.android.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.taaggg.notes.android.app.MainActivity
import com.taaggg.notes.android.app.NotesApp
import com.taaggg.notes.android.app.wiring.AppDependencies
import com.taaggg.notes.android.common.navigation.Screen
import com.taaggg.notes.android.common.scoping.UserDependencies

@Composable
fun Routing(navController: NavHostController, innerPadding: PaddingValues) {

    val app = LocalContext.current.applicationContext as NotesApp
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