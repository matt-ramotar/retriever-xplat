@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

sealed class ThreadNote {
    @Serializable
    data class Network(
        val _id: String,
        val threadId: String,
        val noteId: String,
        val parentNoteId: String,
    ) : ThreadNote()

    @Serializable
    sealed class Output : ThreadNote() {
        @Serializable
        data class Populated(
            val id: String,
            val thread: Thread.Output.Populated,
            val note: Note.Output.Populated,
            val parentNote: Note.Output.Populated,
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val threadId: String,
            val noteId: String,
            val parentNoteId: String,
        ) : Output()

        @Serializable
        data class Node(
            val id: String,
            val threadId: String,
            val noteId: String,
            val parentNoteId: String,
        ) : Output()
    }
}