package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.rest.collection.MentionRestApi
import ai.wandering.retriever.common.storekit.entity.Mention
import ai.wandering.retriever.common.storekit.result.RequestResult

class RealMentionRestApi : MentionRestApi {
    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): RequestResult<Mention.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(input: Mention.Output.Unpopulated): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }
}