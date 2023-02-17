@file:Suppress("PropertyName")
@file:OptIn(ExperimentalSerializationApi::class)

package ai.wandering.retriever.common.storekit.entities

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed class UserNotification {

    @Serializable
    enum class Type(val value: String) {
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
            "TaggedInNote",
        )
        NoteCommentedOn("NoteCommentedOn"),
        ThreadCommentedOn("ThreadCommentedOn"),
        NoteSubscribedTo("NoteSubscribedTo"),
        ThreadSubscribedTo("ThreadSubscribedTo"),
        ChannelSubscribedTo("ChannelSubscribedTo"),
        GraphSubscribedTo("GraphSubscribedTo"),
        NotePinned("NotePinned"),
        ThreadPinned("ThreadPinned"),
        GraphPinned("GraphPinned"),
        SubscribedTo("SubscribedTo"),
        ChannelStarred("ChannelStarred"),
        GraphStarred("GraphStarred"),
        NoteUpvoted("NoteUpvoted"),
        ThreadUpvoted("ThreadUpvoted"),
        NoteReactedTo("NoteReactedTo"),
        ThreadReactedTo("ThreadReactedTo"),
        TaggedInNote("TaggedInNote"),
    }

    @Serializable
    data class Network(
        val _id: String,
        val actionId: String,
        val type: String
    ) : UserNotification()

    @Serializable
    sealed class Output : UserNotification() {
        @Serializable
        data class Populated<out T : Any>(
            val id: String,
            val action: UserAction.Output.Populated<T>,
            val type: Type
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val actionId: String,
            val type: Type
        ) : Output()
    }
}