package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.UserAction
import kotlinx.coroutines.flow.StateFlow

interface UserActionPagingRepository {

    val userActions: StateFlow<List<UserAction.Output.Populated<*>>?>
    suspend fun load()
}