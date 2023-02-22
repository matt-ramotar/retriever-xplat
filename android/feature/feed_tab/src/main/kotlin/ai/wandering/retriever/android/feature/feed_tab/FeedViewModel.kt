package ai.wandering.retriever.android.feature.feed_tab

import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.repository.UserActionPagingRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FeedViewModel(private val userActionPagingRepository: UserActionPagingRepository) : ViewModel() {
    private val _feed: MutableStateFlow<List<UserAction.Output.Populated<*>>?> = MutableStateFlow(null)
    val feed: StateFlow<List<UserAction.Output.Populated<*>>?> = _feed

    init {
        viewModelScope.launch {
            userActionPagingRepository.load()
            userActionPagingRepository.userActions.collectLatest {
                _feed.value = it
            }

        }
    }

    fun load() = viewModelScope.launch { userActionPagingRepository.load() }
}

