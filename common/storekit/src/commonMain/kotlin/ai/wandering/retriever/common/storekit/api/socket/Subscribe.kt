package ai.wandering.retriever.common.storekit.api.socket

import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.coroutines.flow.Flow

interface Subscribe<Id : Any, Response : Any> {
    fun subscribe(id: Id): Flow<RequestResult<Response>>
}