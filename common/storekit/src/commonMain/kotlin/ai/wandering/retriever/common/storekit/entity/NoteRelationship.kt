@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

sealed class NoteRelationship {

    @Serializable
    enum class Type(val value: String) {
        ChildToParent("ChildToParent"),
        ParentToChild("ParentToChild"),
        UnidirectionalReference("UnidirectionalReference"),
        BidirectionalReference("BidirectionalReference");

        companion object {
            private val valueToType = Type.values().associateBy { it.value }
            fun lookup(value: String): Type = valueToType[value]!!
        }
    }

    @Serializable
    data class Network(
        val _id: String,
        val noteId: String,
        val otherNoteId: String,
        val type: String
    ) : NoteRelationship()

    @Serializable
    sealed class Output : NoteRelationship() {
        @Serializable
        data class Populated(
            val id: String,
            val note: Note.Output.Populated.Created,
            val otherNote: Note.Output.Populated.Created,
            val type: Type
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val noteId: String,
            val otherNoteId: String,
            val type: Type
        ) : Output()

        @Serializable
        data class Node(
            val id: String,
            val noteId: String,
            val otherNoteId: String,
            val type: Type
        ) : Output()
    }
}