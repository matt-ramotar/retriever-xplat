@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

sealed class Mention {
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
            val user: User.Output.Node,
            val otherUser: User.Output.Node,

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

        @Serializable
        data class Node(
            val id: String,
            val userId: String,
            val otherUserId: String
        ) : Output()
    }
}