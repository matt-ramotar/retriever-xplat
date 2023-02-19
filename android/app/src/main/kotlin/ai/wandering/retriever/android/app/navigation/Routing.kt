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
import ai.wandering.retriever.common.storekit.db.queries.note.findAndPopulate
import ai.wandering.retriever.common.storekit.db.queries.user.findAndPopulate
import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.UserNotification
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable


@Serializable
data class NotificationsResponse(
    val notifications: List<UserNotification.Output.Unpopulated>
)

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
    val api = appDependencies.api
    val userNotificationsRepository = userDependencies.userNotificationsRepository
    val notifications = userNotificationsRepository.notifications.collectAsState()


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
            val userActionsQuery = database.localUserQueries.findUserActionsByUserId(user.followedUserIds).executeAsList()
            val userActions = userActionsQuery.map { UserAction.Output.Unpopulated(id = it.id, it.userId, it.objectId, it.type) }.distinct()

            Column {

                Text("Activity")

                userActions.forEach {
                    Text(text = it.type.name)
                }
            }
        }
        composable(Screen.Notification.route) {
            Column {
                Text(text = "Notifications")

                notifications.value.forEach {
                    Text(text = it.type.name)
                }
            }
        }
        composable(Screen.Search.route) {
            SearchTab()
        }
        composable(Screen.Account.route) {
            // TODO(mramotar)
            val populatedUser = database.localUserQueries.findAndPopulate(user.id)
            AccountTab(populatedUser) {
                navController.navigate("users/${user.id}")
            }
        }

        composable("users/{userId}", arguments = listOf(navArgument("userId") { type = NavType.StringType })) {
            val userId = requireNotNull(it.arguments?.getString("userId"))
            // TODO(mramotar)
            val populatedOtherUser = database.localUserQueries.findAndPopulate(userId)
            ProfileScreen(user = populatedOtherUser)
        }

        composable("notes/m/{otherUserId}", arguments = listOf(navArgument("otherUserId") { type = NavType.StringType })) {
            val otherUserId = requireNotNull(it.arguments?.getString("otherUserId"))
            val otherUser = database.localUserQueries.findById(otherUserId).executeAsOne()
            val notes = database.localNoteQueries.findByMention(user.id, otherUserId).executeAsList()

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

                database.localNoteQueries.findByTagName(tag).executeAsList().forEach { row ->
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
                        Text(text = channel.id)
                    }
                }

                note.mentions.forEach { mention ->
                    Row {
                        Text(text = "@")
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = mention.otherUserId)
                    }
                }
            }
        }
    }
}