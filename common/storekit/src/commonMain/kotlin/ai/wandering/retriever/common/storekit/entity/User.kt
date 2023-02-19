@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

typealias AuthenticatedPopulatedUser = User.Output.Populated
typealias AuthenticatedUser = User.Output.Unpopulated

sealed class User {
    @Serializable
    data class Network(
        val _id: String,
        val name: String,
        val username: String,
        val email: String,
        val avatarUrl: String? = null,
        val coverImageUrl: String? = null,
        val bio: String? = null,

        // Relationships
        val noteIds: List<String>,
        val graphIds: List<String>,
        val followedGraphIds: List<String>,
        val followedTagIds: List<String>,
        val followedUserIds: List<String>,
        val followerIds: List<String>,
        val pinnedChannelIds: List<String>,
        val pinnedGraphIds: List<String>,
        val pinnedNoteIds: List<String>,
        val actionIds: List<String>,
    ) : User()

    @Serializable
    sealed class Output : User() {
        @Serializable
        data class Populated(
            val id: String,
            val name: String,
            val username: String,
            val email: String,
            val avatarUrl: String? = null,
            val coverImageUrl: String? = null,
            val bio: String? = null,

            // Relationships
            val notes: List<Note.Output.Node>,
            val graphs: List<Graph.Output.Node>,
            val followedGraphs: List<Graph.Output.Node>,
            val followedTags: List<Tag.Output.Node>,
            val followedUsers: List<Node>,
            val followers: List<Node>,
            val pinnedChannels: List<Channel.Output.Node>,
            val pinnedGraphs: List<Graph.Output.Node>,
            val pinnedNotes: List<Note.Output.Node>,
            val userActions: List<UserAction.Output.Node>
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val name: String,
            val username: String,
            val email: String,
            val avatarUrl: String? = null,
            val coverImageUrl: String? = null,
            val bio: String? = null,

            // Relationships
            val noteIds: List<String>,
            val graphIds: List<String>,
            val followedGraphIds: List<String>,
            val followedTagIds: List<String>,
            val followedUserIds: List<String>,
            val followerIds: List<String>,
            val pinnedChannelIds: List<String>,
            val pinnedGraphIds: List<String>,
            val pinnedNoteIds: List<String>,
            val actionIds: List<String>,
        ) : Output()

        @Serializable
        data class Node(
            val id: String,
            val name: String,
            val username: String,
            val email: String,
            val avatarUrl: String? = null,
            val coverImageUrl: String? = null,
            val bio: String? = null,
        ) : Output()
    }
}