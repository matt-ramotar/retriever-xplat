package com.taaggg.retriever.android.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taaggg.retriever.android.app.MainActivity
import com.taaggg.retriever.android.app.RetrieverApp
import com.taaggg.retriever.android.app.wiring.AppDependencies
import com.taaggg.retriever.android.common.navigation.Screen
import com.taaggg.retriever.android.common.scoping.UserDependencies
import com.taaggg.retriever.android.feature.account_tab.AccountTab
import com.taaggg.retriever.android.feature.home_tab.HomeTab
import com.taaggg.retriever.android.feature.search_tab.SearchTab

@Composable
fun Routing(navController: NavHostController, innerPadding: PaddingValues) {

    val app = LocalContext.current.applicationContext as RetrieverApp
    val mainActivity = LocalContext.current as MainActivity

    val appComponent = app.component
    val userComponent = mainActivity.component
    val appDependencies = appComponent as AppDependencies
    val userDependencies = userComponent as UserDependencies

    NavHost(
        navController = navController, startDestination = Screen.Home.route, modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp)
    ) {
        composable(Screen.Home.route) {
            HomeTab(
                tags = appDependencies.tagCollection(),
                mentions = appDependencies.mentionCollection()
            ) {
                navController.navigate("notes/s/$it")
            }
        }
        composable(Screen.Notification.route) {}
        composable(Screen.Search.route) {
            SearchTab()
        }
        composable(Screen.Account.route) {
            AccountTab(userComponent.user)
        }

        composable("notes/s/{tag}", arguments = listOf(navArgument("tag") { type = NavType.StringType })) {

            val tag = it.arguments?.getString("tag")

            Column() {
                Text(tag ?: "Screen")
                Spacer(modifier = Modifier.size(32.dp))
                appDependencies.noteCollection().find(tag ?: "").forEach { note ->

                    Row(modifier = Modifier.clickable { navController.navigate("notes/${note.id}") }) {
                        Text(text = note.content)
                    }
                }
            }
        }

        composable("notes/{noteId}", arguments = listOf(navArgument("noteId") { type = NavType.StringType })) {

            val noteId = requireNotNull(it.arguments?.getString("noteId"))

            val note = requireNotNull(appDependencies.noteCollection().findById(noteId))

            Column() {
                Text(text = note.content)

                note.tags.forEach { tag ->
                    Row {
                        Text(text = "#")
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = tag.name)
                    }
                }
            }
        }
    }
}