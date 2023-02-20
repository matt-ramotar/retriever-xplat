package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.repository.NoteRepository
import ai.wandering.retriever.common.storekit.store.NoteStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse

class RealNoteRepository(
    private val noteStore: NoteStore,
    private val scope: CoroutineScope
) : NoteRepository {
    override fun streamAll(userId: String): Flow<List<Note.Output.Populated>?> = channelFlow {
    }

    override suspend fun get(noteId: String): Note.Output.Populated? =
        noteStore.stream<Note.Output.Populated>(StoreReadRequest.cached(noteId, refresh = true)).firstOrNull()?.dataOrNull()

    @OptIn(ExperimentalStoreApi::class)
    override suspend fun create(input: Note.Output.Populated): Boolean {

        val request = StoreWriteRequest.of<String, Note.Output.Populated, Boolean>(
            key = input.id,
            value = input,
            created = input.createdAt.toEpochMilliseconds()
        )

        return try {
            when (noteStore.write(request)) {
                is StoreWriteResponse.Error -> false
                is StoreWriteResponse.Success -> true
            }
        } catch (error: Throwable) {
            false
        }
    }

    override fun stream(noteId: String): Flow<Note.Output.Populated?> =
        noteStore.stream<Note.Output.Populated>(StoreReadRequest.cached(noteId, refresh = true)).map { it.dataOrNull() }
}