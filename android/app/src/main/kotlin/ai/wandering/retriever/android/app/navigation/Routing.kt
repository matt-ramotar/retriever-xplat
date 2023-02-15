package ai.wandering.retriever.android.app.navigation

import ai.wandering.retriever.android.app.MainActivity
import ai.wandering.retriever.android.app.RetrieverApp
import ai.wandering.retriever.android.app.wiring.AppDependencies
import ai.wandering.retriever.android.common.navigation.Screen
import ai.wandering.retriever.android.common.scoping.UserDependencies
import ai.wandering.retriever.android.feature.account_tab.AccountTab
import ai.wandering.retriever.android.feature.finder_tab.FinderTab
import ai.wandering.retriever.android.feature.finder_tab.ProfileScreen
import ai.wandering.retriever.android.feature.search_tab.SearchTab
import ai.wandering.retriever.common.storekit.extension.findAndPopulate
import ai.wandering.retriever.common.storekit.extension.findAndPopulateByUserId
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

@Composable
fun Routing(navController: NavHostController, innerPadding: PaddingValues) {

    val app = LocalContext.current.applicationContext as RetrieverApp
    val mainActivity = LocalContext.current as MainActivity

    val appComponent = app.component
    val userComponent = mainActivity.component
    val appDependencies = appComponent as AppDependencies
    val userDependencies = userComponent as UserDependencies
    val database = appDependencies.database
    val user = userComponent.user

    NavHost(
        navController = navController, startDestination = Screen.Finder.route, modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp)
    ) {
        composable(Screen.Home.route) {
            Text("Home")
        }
        composable(Screen.Finder.route) {
            FinderTab(
                user = user,
                tags = database.localTagQueries,
                mentions = database.localMentionQueries,
                onNavigateToMentionResults = { navController.navigate("notes/m/$it") },
                onNavigateToSearchTab = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToProfile = {
                    navController.navigate("users/${user.id}")
                },
                onNavigateToTagResults = { navController.navigate("notes/t/$it") }
            )
        }
        composable(Screen.Activity.route) {
            Text("Activity")
        }
        composable(Screen.Notification.route) {}
        composable(Screen.Search.route) {
            SearchTab()
        }
        composable(Screen.Account.route) {
            AccountTab(userComponent.user) {
                navController.navigate("users/${user.id}")
            }
        }

        composable("users/{userId}", arguments = listOf(navArgument("userId") { type = NavType.StringType })) {
            val userId = requireNotNull(it.arguments?.getString("userId"))
            val otherUser = database.localUserQueries.findAndPopulateByUserId(userId)
            ProfileScreen(user = otherUser)
        }

        composable("notes/m/{otherUserId}", arguments = listOf(navArgument("otherUserId") { type = NavType.StringType })) {

            val otherUserId = requireNotNull(it.arguments?.getString("otherUserId"))
            println(user.id)

            println(otherUserId)
            val otherUser = database.localUserQueries.getById(otherUserId).executeAsOne()
            val notes = database.localNoteQueries.getByMention(user.id, otherUserId).executeAsList()

            Column {
                Text(otherUser.name, modifier = Modifier.clickable { navController.navigate("users/$otherUserId") })
                Spacer(modifier = Modifier.size(32.dp))

                notes.forEach { row ->
                    Row(modifier = Modifier.clickable { navController.navigate("notes/${row.id}") }) {
                        Text(text = row.content ?: "")
                    }
                }
            }
        }

        composable("notes/t/{tag}", arguments = listOf(navArgument("tag") { type = NavType.StringType })) {

            val tag = requireNotNull(it.arguments?.getString("tag"))

            Column() {
                Text(tag ?: "Screen")
                Spacer(modifier = Modifier.size(32.dp))

                database.localNoteQueries.getByTagName(tag).executeAsList().forEach { row ->
                    Row(modifier = Modifier.clickable { navController.navigate("notes/${row.noteId}") }) {
                        Text(text = row.content ?: "")
                    }
                }
            }
        }

        composable("notes/{noteId}", arguments = listOf(navArgument("noteId") { type = NavType.StringType })) {
            val noteId = requireNotNull(it.arguments?.getString("noteId"))
            val note = database.localNoteQueries.findAndPopulate(noteId)

            Column() {
                Text(text = note.content)

                note.channels.forEach { channel ->
                    Row {
                        Text(text = "#")
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = channel.tag.name)
                    }
                }

                note.mentions.forEach { mention ->
                    Row {
                        Text(text = "@")
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = mention.otherUser.name)
                    }
                }
            }
        }
    }
}