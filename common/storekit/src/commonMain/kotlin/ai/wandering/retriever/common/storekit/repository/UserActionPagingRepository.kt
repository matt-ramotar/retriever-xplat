package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import kotlinx.coroutines.flow.Flow

interface UserActionPagingRepository {
    suspend fun get(pageId: Int): PagingResponse<Int, UserAction.Output.Populated<*>>
}