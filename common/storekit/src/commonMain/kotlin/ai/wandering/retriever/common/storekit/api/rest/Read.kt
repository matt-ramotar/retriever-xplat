package ai.wandering.retriever.common.storekit.api.rest

import ai.wandering.retriever.common.storekit.result.RequestResult

interface Read<Id : Any, Network : Any> {
    suspend fun get(id: Id): RequestResult<Network>
}