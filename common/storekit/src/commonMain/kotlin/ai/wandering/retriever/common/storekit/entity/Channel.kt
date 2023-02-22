@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed class Channel {
    @Serializable
    sealed class Network : Channel(), Identifiable.Network {
        @Serializable
        data class Unpopulated(
            override val _id: String,
            val createdAt: String,
            val userId: String,
            val graphId: String,
            val tagId: String,

            // Relationships
            val noteIds: List<String>,
            val pinnerIds: List<String>
        ) : Network()

        @Serializable
        data class Populated(
            override val _id: String,
            val createdAt: Instant,
            val user: User.Network,
            val graph: Graph.Network.Node,
            val tag: Tag.Network,

            // Relationships
            val notes: List<Note.Network.Node>,
            val pinners: List<User.Network>
        ) : Network()

        @Serializable
        data class Node(
            override val _id: String,
            val userId: String,
            val graphId: String,
            val tagId: String,
            val createdAt: Instant
        ) : Network()
    }

    @Serializable
    sealed class Output : Channel(), Identifiable.Output {
        @Serializable
        data class Populated(
            override val id: String,
            val createdAt: Instant,
            val user: User.Output.Node,
            val graph: Graph.Output.Node,
            val tag: Tag.Output.Node,

            // Relationships
            val notes: List<Note.Output.Node>,
            val pinners: List<User.Output.Node>
        ) : Output()

        @Serializable
        data class Unpopulated(
            override val id: String,
            val createdAt: Instant,
            val userId: String,
            val graphId: String,
            val tagId: String,

            // Relationships
            val noteIds: List<String>,
            val pinnerIds: List<String>
        ) : Output()

        @Serializable
        data class Node(
            override val id: String,
            val userId: String,
            val graphId: String,
            val tagId: String,
            val createdAt: Instant
        ) : Output()
    }

}