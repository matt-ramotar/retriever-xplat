package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun streamAll(userId: String): Flow<List<Note.Output.Populated>?>
    fun stream(noteId: String): Flow<Note.Output.Populated?>
    suspend fun get(noteId: String): Note.Output.Populated?
}