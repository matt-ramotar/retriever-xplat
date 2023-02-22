package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.repository.UserActionPagingRepository
import ai.wandering.retriever.common.storekit.store.UserActionPagingStore
import kotlinx.coroutines.flow.first
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse


class RealUserActionPagingRepository(private val userActionPagingStore: UserActionPagingStore) : UserActionPagingRepository {
    override suspend fun get(pageId: Int): PagingResponse<Int, UserAction.Output.Populated<*>> {
        val request: StoreReadRequest<Int> = StoreReadRequest.fresh(pageId)

        return when (val storeResponse = userActionPagingStore.stream<Boolean>(request).first { it.dataOrNull() != null }) {
            is StoreReadResponse.Data -> storeResponse.value
            else -> PagingResponse.Error
        }
    }

}