@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entities

import ai.wandering.retriever.common.storekit.entities.note.Mention
import ai.wandering.retriever.common.storekit.entities.user.output.User
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


sealed class Note {

    @Serializable
    data class Network(
        val _id: String,
        val userId: String,
        val content: String,
        val isRead: Boolean,
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
            val user: User,
            val content: String,
            val isRead: Boolean,
            val createdAt: Instant,
            val updatedAt: Instant,

            // Relationships
            val channels: List<Channel.Output.Populated>,
            val mentions: List<Mention>,
            val noteRelationships: List<String>,
            val threadNotes: List<String>,
            val pinners: List<String>,
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

    }
}