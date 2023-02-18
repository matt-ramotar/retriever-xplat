package ai.wandering.retriever.common.storekit.api.impl.rest

import ai.wandering.retriever.common.storekit.api.rest.collection.NoteRestApi
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.result.RequestResult

class RealNoteRestApi : NoteRestApi {
    override suspend fun delete(id: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): RequestResult<Note.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(input: Note.Output.Unpopulated): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }
}