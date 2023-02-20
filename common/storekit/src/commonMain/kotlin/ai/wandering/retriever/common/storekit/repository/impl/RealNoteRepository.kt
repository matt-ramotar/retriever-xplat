package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.repository.NoteRepository
import ai.wandering.retriever.common.storekit.store.NoteStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.StoreReadRequest

class RealNoteRepository(
    private val noteStore: NoteStore,
) : NoteRepository {
    override fun streamAll(userId: String): Flow<List<Note.Output.Populated>?> = channelFlow {
    }

    override suspend fun get(noteId: String): Note.Output.Populated? =
        noteStore.stream<Note.Output.Populated>(StoreReadRequest.cached(noteId, refresh = true)).firstOrNull()?.dataOrNull()

    override fun stream(noteId: String): Flow<Note.Output.Populated?> =
        noteStore.stream<Note.Output.Populated>(StoreReadRequest.cached(noteId, refresh = true)).map { it.dataOrNull() }
}