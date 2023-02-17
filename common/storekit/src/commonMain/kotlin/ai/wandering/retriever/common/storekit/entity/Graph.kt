@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

sealed class Graph {
    @Serializable
    data class Network(
        val _id: String,
        val userId: String,
        val name: String,

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

            // Relationships
            val followers: List<User.Output.Unpopulated>,
            val pinners: List<User.Output.Unpopulated>
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val userId: String,
            val name: String,

            // Relationships
            val followerIds: List<String>,
            val pinnerIds: List<String>
        ) : Output()
    }
}