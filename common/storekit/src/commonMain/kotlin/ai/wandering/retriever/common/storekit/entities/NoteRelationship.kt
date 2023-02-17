@file:Suppress("PropertyName")
@file:OptIn(ExperimentalSerializationApi::class)

package ai.wandering.retriever.common.storekit.entities

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed class NoteRelationship {

    @Serializable
    enum class Type(val value: String) {
        @JsonNames(
            "ChildToParent",
            "ParentToChild",
            "UnidirectionalReference",
            "BidirectionalReference"
        )
        ChildToParent("ChildToParent"),
        ParentToChild("ParentToChild"),
        UnidirectionalReference("UnidirectionalReference"),
        BidirectionalReference("BidirectionalReference"),
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
            val note: Note.Output.Populated,
            val otherNote: Note.Output.Populated,
            val type: Type
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val noteId: String,
            val otherNoteId: String,
            val type: Type
        ) : Output()
    }
}