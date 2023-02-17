package ai.wandering.retriever.common.storekit.api.rest

import ai.wandering.retriever.common.storekit.result.RequestResult

interface Delete<Id : Any> {
    suspend fun delete(id: Id): RequestResult<Boolean>
}