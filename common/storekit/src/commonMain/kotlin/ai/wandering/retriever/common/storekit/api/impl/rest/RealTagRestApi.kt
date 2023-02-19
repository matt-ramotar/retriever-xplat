package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.rest.collection.TagRestApi
import ai.wandering.retriever.common.storekit.entity.Tag
import ai.wandering.retriever.common.storekit.result.RequestResult
import io.ktor.client.HttpClient

class RealTagRestApi(private val client: HttpClient) : TagRestApi {
    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): RequestResult<Tag.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(input: Tag.Output.Unpopulated): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }
}