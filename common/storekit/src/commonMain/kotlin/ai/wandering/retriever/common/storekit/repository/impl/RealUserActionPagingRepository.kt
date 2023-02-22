package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.repository.UserActionPagingRepository
import ai.wandering.retriever.common.storekit.store.UserActionPagingStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

class RealUserActionPagingRepository(private val userActionPagingStore: UserActionPagingStore) : UserActionPagingRepository {

    private val _userActions: MutableStateFlow<List<UserAction.Output.Populated<*>>?> = MutableStateFlow(null)
    override val userActions: StateFlow<List<UserAction.Output.Populated<*>>?> = _userActions

    private val nextPageId = MutableStateFlow<Int?>(1)

    override suspend fun load() {

        println("Repository")
        if (nextPageId.value != null) {
            val request: StoreReadRequest<Int> = StoreReadRequest.fresh(nextPageId.value!!)
            when (val storeResponse = userActionPagingStore.stream<Boolean>(request).first { it.dataOrNull() != null }) {
                is StoreReadResponse.Data -> {

                    val pagingResponse = storeResponse.value
                    if (pagingResponse is PagingResponse.Data) {


                        if (_userActions.value == null) {
                            _userActions.value = pagingResponse.objects
                        } else {
                            val nextUserActions = _userActions.value!!.toMutableList()
                            nextUserActions.addAll(pagingResponse.objects)
                            _userActions.value = nextUserActions
                        }

                        nextPageId.value = pagingResponse.nextPageId
                    }
                }

                else -> {
                    println(storeResponse.toString())
                    // TODO(mramotar)
                }
            }
        }

    }

}