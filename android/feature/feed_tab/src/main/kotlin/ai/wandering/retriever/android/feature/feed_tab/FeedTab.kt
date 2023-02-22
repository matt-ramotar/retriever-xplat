package ai.wandering.retriever.android.feature.feed_tab

import ai.wandering.retriever.common.storekit.entity.UserAction
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FeedTab(feedViewModel: FeedViewModel = viewModel()) {
    val feed = feedViewModel.feed.collectAsState()

    Column {
        when (val userActions = feed.value) {
            null -> {}
            else -> {
                userActions.forEach { userAction ->
                    when (userAction.model) {
                        UserAction.Model.Note -> {
                            Text(text = "Note")
                        }

                        UserAction.Model.Thread -> {
                            Text(text = "Thread")
                        }

                        UserAction.Model.Channel -> {
                            Text(text = "Channel")
                        }

                        UserAction.Model.Graph -> {
                            Text(text = "Graph")
                        }

                        UserAction.Model.User -> {
                            Text(text = "User")
                        }
                    }
                }
            }
        }
    }

}