package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.rest.collection.GraphRestApi
import ai.wandering.retriever.common.storekit.entity.Graph
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient

class RealGraphRestApi(private val client: HttpClient) : GraphRestApi {
    override suspend fun create(request: Graph.Request.Create): RequestResult<Graph.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): RequestResult<Graph.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun update(request: Graph.Request.Update): RequestResult<Graph.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

}
