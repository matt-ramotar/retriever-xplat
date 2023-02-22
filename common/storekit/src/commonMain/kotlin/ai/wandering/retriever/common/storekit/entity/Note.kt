@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


sealed class Note {

    @Serializable
    sealed class Network : Note(), Identifiable.Network {
        @Serializable
        data class Unpopulated(
            override val _id: String,
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
            override val _id: String,
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
            override val _id: String,
            val userId: String,
            val content: String,
            val is_read: Boolean,
            val createdAt: String,
            val updatedAt: String,
        ) : Network()

    }


    @Serializable
    sealed class Output : Note(), Identifiable.Output {

        @Serializable
        sealed class Populated : Output() {
            abstract val user: User.Output.Node
            abstract val content: String
            abstract val isRead: Boolean
            abstract val createdAt: Instant
            abstract val updatedAt: Instant

            // Relationships
            abstract val channels: List<Channel.Output.Node>
            abstract val mentions: List<Mention.Output.Node>
            abstract val noteRelationships: List<NoteRelationship.Output.Node>
            abstract val threadNotes: List<ThreadNote.Output.Node>
            abstract val pinners: List<User.Output.Node>

            @Serializable
            data class Draft(
                override val id: String,
                override val user: User.Output.Node,
                override val content: String,
                override val isRead: Boolean,
                override val createdAt: Instant,
                override val updatedAt: Instant,
                override val channels: List<Channel.Output.Node>,
                override val mentions: List<Mention.Output.Node>,
                override val noteRelationships: List<NoteRelationship.Output.Node>,
                override val threadNotes: List<ThreadNote.Output.Node>,
                override val pinners: List<User.Output.Node>

            ) : Populated()

            @Serializable
            data class Created(
                override val id: String,
                override val user: User.Output.Node,
                override val content: String,
                override val isRead: Boolean,
                override val createdAt: Instant,
                override val updatedAt: Instant,
                override val channels: List<Channel.Output.Node>,
                override val mentions: List<Mention.Output.Node>,
                override val noteRelationships: List<NoteRelationship.Output.Node>,
                override val threadNotes: List<ThreadNote.Output.Node>,
                override val pinners: List<User.Output.Node>
            ) : Populated()
        }

        @Serializable
        data class Unpopulated(
            override val id: String,
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
            override val id: String,
            val userId: String,
            val content: String,
            val isRead: Boolean,
            val createdAt: Instant,
            val updatedAt: Instant,
        ) : Output()

    }
}