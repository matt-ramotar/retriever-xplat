@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed class Channel {
    @Serializable
    sealed class Network : Channel() {
        @Serializable
        data class Unpopulated(
            val _id: String,
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
            val _id: String,
            val createdAt: Instant,
            val user: User.Network,
            val graph: Graph.Network,
            val tag: Tag.Network,

            // Relationships
            val notes: List<Note.Network.Node>,
            val pinners: List<User.Network>
        ) : Output()

        @Serializable
        data class Node(
            val _id: String,
            val userId: String,
            val graphId: String,
            val tagId: String,
            val createdAt: Instant
        )
    }

    @Serializable
    sealed class Output : Channel() {
        @Serializable
        data class Populated(
            val id: String,
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
            val id: String,
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
            val id: String,
            val userId: String,
            val graphId: String,
            val tagId: String,
            val createdAt: Instant
        ) : Output()
    }

}