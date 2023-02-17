package ai.wandering.retriever.common.storekit.api.rest

import ai.wandering.retriever.common.storekit.result.RequestResult

interface Update<Request : Any, Response : Any> {
    suspend fun update(request: Request): RequestResult<Response>
}