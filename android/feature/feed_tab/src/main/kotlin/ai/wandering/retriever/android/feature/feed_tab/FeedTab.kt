package ai.wandering.retriever.android.feature.feed_tab

import ai.wandering.retriever.android.common.sig.color.systemThemeColors
import ai.wandering.retriever.common.storekit.entity.UserAction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    totalItems: Int = listState.layoutInfo.totalItemsCount,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItems - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
            }
    }
}


@Composable
fun FeedTab(feedViewModel: FeedViewModel = viewModel()) {
    val feed = feedViewModel.feed.collectAsState()
    val colors = systemThemeColors()

    val state = rememberLazyListState()
    val totalItems = feedViewModel.totalItems.collectAsState()

    val update by remember {
        val data = mutableStateListOf<UserAction.Output.Populated<*>>()

        derivedStateOf {

            feed.value.run {
                data.addAll(this ?: listOf())
                println("ADD ALL")
            }

            data
        }
    }


    LazyColumn(state = state) {
        item {
            Text(text = "Total items: ${totalItems.value}")
        }

        itemsIndexed(update) { index, item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = index.toString())
                Text(text = item.id)
            }

        }
    }

    InfiniteListHandler(listState = state, totalItems = totalItems.value) {
        feedViewModel.load()
    }

//    LazyColumn {
//        when (val userActions = feed.value) {
//            null -> {}
//            else -> {
//                userActions.forEachIndexed { index, userAction ->
//                    when (userAction.model) {
//                        UserAction.Model.Note -> {
//                            item {
//
//                                Text(text = "Note")
//                            }
//                        }
//
//                        UserAction.Model.Thread -> {
//                            item {
//
//                                Text(text = "Thread")
//                            }
//                        }
//
//                        UserAction.Model.Channel -> {
//                            item {
//
//                                Text(text = "Channel")
//                            }
//                        }
//
//                        UserAction.Model.Graph -> {
//                            item {
//                                Column(
//                                    modifier = Modifier
//                                        .padding(8.dp)
//                                        .background(colors.red.background)
//                                        .fillMaxWidth()
//                                ) {
//                                    Text(text = "Index: $index")
////                                    Text(text = "Id: ${userAction.id}")
////                                    Text(text = "User: ${userAction.user}")
////                                    Text(text = "Obj: ${userAction.obj}")
////                                    Text(text = "Model: ${userAction.model}")
////                                    Text(text = "Type: ${userAction.type}")
//                                }
//                            }
//                        }
//
//                        UserAction.Model.User -> {
//                            item {
//                                Text(text = "User")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

}