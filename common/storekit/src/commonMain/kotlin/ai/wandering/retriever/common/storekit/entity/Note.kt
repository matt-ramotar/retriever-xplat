@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


sealed class Note {

    @Serializable
    sealed class Network : Note() {
        @Serializable
        data class Unpopulated(
            val _id: String,
            val userId: String,
            val content: String,
            val is_read: Boolean,
            val createdAt: String,
            val updatedAt: String,

            // Relationships
            val channelIds: List<String>,
            val mentionIds: List<String>,
            val noteRelationshipIds: List<String>,
            val threadNoteIds: List<String>,
            val pinnerIds: List<String>,
        ) : Network()

        @Serializable
        data class Populated(
            val _id: String,
            val user: User.Network,
            val content: String,
            val is_read: Boolean,
            val createdAt: String,
            val updatedAt: String,

            // Relationships
            val channels: List<Channel.Network.Node>,
            val mentions: List<Mention.Network>,
            val noteRelationships: List<NoteRelationship.Network>,
            val threadNotes: List<ThreadNote.Network>,
            val pinners: List<User.Network>,
        ) : Network()

        @Serializable
        data class Node(
            val _id: String,
            val userId: String,
            val content: String,
            val is_read: Boolean,
            val createdAt: String,
            val updatedAt: String,
        ) : Network()

    }


    @Serializable
    sealed class Output : Note() {
        @Serializable
        data class Populated(
            val id: String,
            val user: User.Output.Node,
            val content: String,
            val isRead: Boolean,
            val createdAt: Instant,
            val updatedAt: Instant,

            // Relationships
            val channels: List<Channel.Output.Node>,
            val mentions: List<Mention.Output.Node>,
            val noteRelationships: List<NoteRelationship.Output.Node>,
            val threadNotes: List<ThreadNote.Output.Node>,
            val pinners: List<User.Output.Node>,
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val userId: String,
            val content: String,
            val isRead: Boolean,
            val createdAt: Instant,
            val updatedAt: Instant,

            // Relationships
            val channelIds: List<String>,
            val mentionIds: List<String>,
            val noteRelationshipIds: List<String>,
            val threadNoteIds: List<String>,
            val pinnerIds: List<String>,
        ) : Output()


        @Serializable
        data class Node(
            val id: String,
            val userId: String,
            val content: String,
            val isRead: Boolean,
            val createdAt: Instant,
            val updatedAt: Instant,
        ) : Output()

    }
}