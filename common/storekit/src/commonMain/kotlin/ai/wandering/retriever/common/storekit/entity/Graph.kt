@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed class Graph {


    @Serializable
    sealed class Network : Graph() {
        @Serializable
        data class Unpopulated(
            val _id: String,
            val userId: String,
            val name: String,
            val createdAt: String,

            // Relationships
            val followerIds: List<String>,
            val pinnerIds: List<String>
        ) : Network()

        @Serializable
        data class Node(
            val _id: String,
            val userId: String,
            val name: String,
            val createdAt: String,
        ) : Network()

        @Serializable
        data class Populated(
            val _id: String,
            val user: User.Network,
            val name: String,
            val createdAt: String,

            // Relationships
            val followers: List<User.Network>,
            val pinners: List<User.Network>
        ) : Network()
    }

    @Serializable
    sealed class Output : Graph() {
        @Serializable
        data class Populated(
            val id: String,
            val user: User.Output.Node,
            val name: String,
            val createdAt: Instant,

            // Relationships
            val followers: List<User.Output.Node>,
            val pinners: List<User.Output.Node>
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val userId: String,
            val name: String,
            val createdAt: Instant,

            // Relationships
            val followerIds: List<String>,
            val pinnerIds: List<String>
        ) : Output()

        @Serializable
        data class Node(
            val id: String,
            val userId: String,
            val name: String,
            val createdAt: Instant
        ) : Output()
    }
}