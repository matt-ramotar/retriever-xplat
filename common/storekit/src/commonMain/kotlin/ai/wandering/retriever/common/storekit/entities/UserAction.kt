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
data class UserNotification(
    val userId: String,
    val otherUserId: String,
    val objectId: String,
    val type: Type
) {
    enum class Type {
        NoteCommentedOn,
        ThreadCommentedOn,
        NoteSubscribedTo,
        ThreadSubscribedTo,
        ChannelSubscribedTo,
        GraphSubscribedTo,
        NotePinned,
        ThreadPinned,
        GraphPinned,
        SubscribedTo,
        ChannelStarred,
        GraphStarred,
        NoteUpvoted,
        ThreadUpvoted,
        NoteReactedTo,
        ThreadReactedTo,
        TaggedInNote
    }
}

@Serializable
data class UserAction(
    val userId: String,
    val objectId: String,
    val type: Type
) {
    enum class Type {
        CreateNote,
        CreateThread,
        CreateChannel,
        CreateGraph,
        CommentOnNote,
        CommentOnThread,
        SubscribeToNote,
        SubscribeToThread,
        SubscribeToChannel,
        SubscribeToGraph,
        SubscribeToUser,
        PinNote,
        PinThread,
        PinChannel,
        PinGraph,
        StarChannel,
        StarGraph,
        UpvoteNote,
        UpvoteThread,
        ReactToNote,
        ReactToThread,
        TaggedInNote
    }
}