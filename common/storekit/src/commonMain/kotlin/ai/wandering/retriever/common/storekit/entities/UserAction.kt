package ai.wandering.retriever.common.storekit.entities

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


@Serializable
data class UserFeedItem(
    val userAction: UserAction,
    val seen: Instant? = null,
    val interactions: List<Instant> = listOf()
)


@Serializable
sealed class UserAction {
    abstract val userId: String

    // Create
    @Serializable
    data class CreateNote(
        override val userId: String,
        val noteId: String,
    ) : UserAction()

    data class CreateThread(
        override val userId: String,
        val threadId: String,
    ) : UserAction()

    data class CreateChannel(
        override val userId: String,
        val channelId: String,
    ) : UserAction()

    data class CreateGraph(
        override val userId: String,
        val graphId: String,
    ) : UserAction()

    // Comment
    data class CommentOnNote(
        override val userId: String,
        val noteId: String
    ) : UserAction()

    data class CommentOnThread(
        override val userId: String,
        val threadId: String
    ) : UserAction()

    // Subscribe
    data class SubscribeToNote(
        override val userId: String,
        val noteId: String
    ) : UserAction()

    data class SubscribeToThread(
        override val userId: String,
        val threadId: String
    ) : UserAction()

    data class SubscribeToChannel(
        override val userId: String,
        val channelId: String
    ) : UserAction()

    data class SubscribeToGraph(
        override val userId: String,
        val graphId: String
    ) : UserAction()

    data class SubscribeToUser(
        override val userId: String,
        val otherUserId: String
    ) : UserAction()

    // Pin
    data class PinNote(
        override val userId: String,
        val noteId: String
    ) : UserAction()

    data class PinThread(
        override val userId: String,
        val threadId: String
    ) : UserAction()

    data class PinChannel(
        override val userId: String,
        val channelId: String
    ) : UserAction()

    data class PinGraph(
        override val userId: String,
        val graphId: String
    ) : UserAction()

    // Star
    data class StarChannel(
        override val userId: String,
        val channelId: String
    ) : UserAction()

    data class StarGraph(
        override val userId: String,
        val graphId: String
    ) : UserAction()

    // Upvote
    data class UpvoteNote(
        override val userId: String,
        val noteId: String
    ) : UserAction()

    data class UpvoteThread(
        override val userId: String,
        val threadId: String
    ) : UserAction()

    // React
    data class ReactToNote(
        override val userId: String,
        val noteId: String
    ) : UserAction()

    data class ReactToThread(
        override val userId: String,
        val threadId: String
    ) : UserAction()

    // Get Tagged
    data class TaggedInNote(
        override val userId: String,
        val noteId: String,
    ) : UserAction()

}

