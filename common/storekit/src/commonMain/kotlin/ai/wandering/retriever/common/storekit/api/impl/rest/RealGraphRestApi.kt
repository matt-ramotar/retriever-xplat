package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.rest.single.GraphRestApi
import ai.wandering.retriever.common.storekit.entity.Graph
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient

class RealGraphRestApi(private val client: HttpClient) : GraphRestApi {
    override suspend fun get(id: String): RequestResult<Graph.Network.Populated> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(input: Graph.Output.Unpopulated): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }
}
