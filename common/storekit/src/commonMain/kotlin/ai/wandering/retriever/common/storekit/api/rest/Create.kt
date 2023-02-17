package ai.wandering.retriever.common.storekit.api.rest

import ai.wandering.retriever.common.storekit.result.RequestResult

interface Create<Request : Any, Response : Any> {
    suspend fun create(request: Request): RequestResult<Response>
}