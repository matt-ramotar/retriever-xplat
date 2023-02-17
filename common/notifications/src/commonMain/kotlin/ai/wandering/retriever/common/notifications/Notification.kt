package ai.wandering.retriever.common.notifications

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Notification(
    @SerialName("_id")
    val id: String,
    val actionId: String,
    val type: Type
) {
    @Serializable
    enum class Type {
        @OptIn(ExperimentalSerializationApi::class)
        @JsonNames(
            "NoteCommentedOn",
            "ThreadCommentedOn",
            "NoteSubscribedTo",
            "ThreadSubscribedTo",
            "ChannelSubscribedTo",
            "GraphSubscribedTo",
            "NotePinned",
            "ThreadPinned",
            "GraphPinned",
            "SubscribedTo",
            "ChannelStarred",
            "GraphStarred",
            "NoteUpvoted",
            "ThreadUpvoted",
            "NoteReactedTo",
            "ThreadReactedTo",
            "TaggedInNote"
        )
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
