@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed class Graph {

    sealed class Request {
        data class Create(
            val userId: String,
            val name: String,
        ) : Request()

        data class Update(
            val graph: Output.Unpopulated
        ) : Request()
    }

    @Serializable
    data class Network(
        val _id: String,
        val userId: String,
        val name: String,
        val createdAt: String,

        // Relationships
        val followerIds: List<String>,
        val pinnerIds: List<String>
    ) : Graph()

    @Serializable
    sealed class Output : Graph() {
        @Serializable
        data class Populated(
            val id: String,
            val userId: String,
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