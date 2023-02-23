@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.app.ui

import ai.wandering.retriever.android.app.navigation.Routing
import ai.wandering.retriever.android.feature.account_tab.CampaignViewModel
import ai.wandering.retriever.android.feature.create_note.NoteCreationViewModel
import ai.wandering.retriever.android.feature.feed_tab.FeedViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun RetrieverScaffold(
    notifications: Int,
    noteCreationViewModel: NoteCreationViewModel = viewModel(),
    feedViewModel: FeedViewModel = viewModel(),
    campaignViewModel: CampaignViewModel = viewModel()
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { RetrieverBottomBar(navController = navController, notifications = notifications) }
    ) { innerPadding ->
        Routing(navController = navController, innerPadding = innerPadding, noteCreationViewModel = noteCreationViewModel, feedViewModel = feedViewModel, campaignViewModel = campaignViewModel)
    }
}