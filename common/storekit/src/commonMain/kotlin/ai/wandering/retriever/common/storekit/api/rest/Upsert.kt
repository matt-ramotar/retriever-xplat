package ai.wandering.retriever.common.storekit.api.rest

import ai.wandering.retriever.common.storekit.result.RequestResult

interface Upsert<Input : Any> {
    suspend fun upsert(input: Input): RequestResult<Boolean>
}