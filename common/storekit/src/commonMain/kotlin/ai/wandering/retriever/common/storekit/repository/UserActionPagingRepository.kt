package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.UserAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserActionPagingRepository {

    val userActions: Flow<List<UserAction.Output.Populated<*>>?>
    suspend fun load()
}