@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

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
        val followedGraphIds: List<String>,
        val followedTagIds: List<String>,
        val followedUserIds: List<String>,
        val followerIds: List<String>,
        val pinnedChannelIds: List<String>,
        val pinnedGraphIds: List<String>,
        val pinnedNoteIds: List<String>
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
            val notes: List<Note.Output.Populated>,
            val followedGraphs: List<Graph.Output.Populated>,
            val followedTags: List<Tag.Output.Populated>,
            val followedUsers: List<Unpopulated>,
            val followers: List<Unpopulated>,
            val pinnedChannels: List<Channel.Output.Populated>,
            val pinnedGraphs: List<Graph.Output.Populated>,
            val pinnedNotes: List<Note.Output.Unpopulated>
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
            val followedGraphIds: List<String>,
            val followedTagIds: List<String>,
            val followedUserIds: List<String>,
            val followerIds: List<String>,
            val pinnedChannelIds: List<String>,
            val pinnedGraphIds: List<String>,
            val pinnedNoteIds: List<String>
        ) : Output()
    }
}