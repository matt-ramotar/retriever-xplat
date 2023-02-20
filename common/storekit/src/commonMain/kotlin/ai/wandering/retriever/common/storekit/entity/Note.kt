@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


sealed class Note {

    @Serializable
    data class Network(
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
    ) : Output()


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
            val noteRelationships: List<NoteRelationship.Output.Unpopulated>,
            val threadNotes: List<ThreadNote.Output.Unpopulated>,
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