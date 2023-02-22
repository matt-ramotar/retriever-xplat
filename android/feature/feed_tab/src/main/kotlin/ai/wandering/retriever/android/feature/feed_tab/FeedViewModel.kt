package ai.wandering.retriever.android.feature.feed_tab

import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.repository.UserActionPagingRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel(private val userActionPagingRepository: UserActionPagingRepository) : ViewModel() {

    private val _feed: MutableStateFlow<List<UserAction.Output.Populated<*>>?> = MutableStateFlow(null)
    val feed: StateFlow<List<UserAction.Output.Populated<*>>?> = _feed


    private val nextPageId = MutableStateFlow<Int?>(1)


    private val _totalItems: MutableStateFlow<Int> = MutableStateFlow(0)
    val totalItems: StateFlow<Int> = _totalItems

    init {
        load()
    }

    fun load() {
        val pageId = nextPageId.value


        if (pageId != null) {
            viewModelScope.launch {
                when (val response = userActionPagingRepository.get(pageId)) {
                    is PagingResponse.Data -> {
                        nextPageId.value = response.nextPageId

                        if (_feed.value == null) {
                            _feed.value = response.objects
                        } else {
                            val nextFeed = _feed.value!!.toMutableList()
                            nextFeed.addAll(response.objects)
                            _feed.value = nextFeed
                        }

                        _totalItems.value = _feed.value?.size ?: 0

                    }

                    PagingResponse.Error -> {
                        // TODO(mramotar): Handle
                    }
                }
            }
        } else {
            // TODO(mramotar): Display EOL
        }
    }
}

