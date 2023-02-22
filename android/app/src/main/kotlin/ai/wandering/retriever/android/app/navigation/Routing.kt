package ai.wandering.retriever.android.app.navigation

import ai.wandering.retriever.android.app.MainActivity
import ai.wandering.retriever.android.app.RetrieverApp
import ai.wandering.retriever.android.app.wiring.AppDependencies
import ai.wandering.retriever.android.common.navigation.Screen
import ai.wandering.retriever.android.common.scoping.UserDependencies
import ai.wandering.retriever.android.feature.account_tab.AccountTab
import ai.wandering.retriever.android.feature.create_note.CreateNoteScreen
import ai.wandering.retriever.android.feature.create_note.NoteCreationViewModel
import ai.wandering.retriever.android.feature.feed_tab.FeedTab
import ai.wandering.retriever.android.feature.feed_tab.FeedViewModel
import ai.wandering.retriever.android.feature.finder_tab.FinderTab
import ai.wandering.retriever.android.feature.finder_tab.ProfileScreen
import ai.wandering.retriever.android.feature.search_tab.SearchTab
import ai.wandering.retriever.common.storekit.db.queries.note.findAndPopulate
import ai.wandering.retriever.common.storekit.db.queries.user.findAndPopulate
import ai.wandering.retriever.common.storekit.entity.Channel
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable


@Serializable
data class NotificationsResponse(
    val notifications: List<UserNotification.Output.Unpopulated>
)

@Composable
fun Routing(navController: NavHostController, innerPadding: PaddingValues, noteCreationViewModel: NoteCreationViewModel = viewModel(), feedViewModel: FeedViewModel = viewModel()) {


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
    val channelsManager = userDependencies.channelsManager

    NavHost(
        navController = navController, startDestination = Screen.Finder.route, modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp)
    ) {
        composable(Screen.Home.route) {
            Text("Home")
        }
        composable(Screen.Finder.route) {
            val channels = channelsManager.channels.collectAsState()

            FinderTab(
                user = user,
                channels = channels.value,
                mentions = database.localMentionQueries,
                onNavigateToMentionResults = { navController.navigate("notes/m/$it") },
                onNavigateToSearchTab = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToProfile = {
                    navController.navigate("users/${user.id}")
                },
                onNavigateToTagResults = { navController.navigate("channels/$it") }
            )
        }
        composable(Screen.Activity.route) {
            FeedTab(feedViewModel = feedViewModel)
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

        composable("channels/{channelId}", arguments = listOf(navArgument("channelId") { type = NavType.StringType })) {
            val channelId = requireNotNull(it.arguments?.getString("channelId"))

            val channelStateFlow = remember { MutableStateFlow<Channel.Output.Populated?>(null) }

            LaunchedEffect(channelId) {
                channelsManager.streamChannel(channelId).collectLatest { channel ->
                    channelStateFlow.value = channel

                }
            }

            when (val channel = channelStateFlow.collectAsState().value) {
                null -> {
                    Column {
                        Text(text = "Loading...")
                    }
                }

                else -> {
                    Column {
                        Text(text = channel.tag.name)

                        channel.notes.forEach { note ->
                            Text(text = note.content)
                        }
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

        composable("create/note") {
            CreateNoteScreen(noteCreationViewModel = noteCreationViewModel)
        }
    }
}