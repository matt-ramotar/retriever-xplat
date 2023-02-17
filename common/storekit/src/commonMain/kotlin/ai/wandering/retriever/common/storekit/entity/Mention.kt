@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

sealed class Mention {

    sealed class Request {
        data class Create(
            val userId: String,
            val otherUserId: String,
        ) : Request()

        data class Update(
            val graph: Graph.Output.Unpopulated
        ) : Request()
    }

    @Serializable
    data class Network(
        val _id: String,
        val userId: String,
        val otherUserId: String,

        // Relationships
        val noteIds: List<String>,
    ) : Mention()

    @Serializable
    sealed class Output : Mention() {
        @Serializable
        data class Populated(
            val id: String,
            val user: User.Output.Unpopulated,
            val otherUser: User.Output.Unpopulated,

            // Relationships
            val notes: List<Note.Output.Unpopulated>,
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val userId: String,
            val otherUserId: String,

            // Relationships
            val noteIds: List<String>,
        ) : Output()
    }
}