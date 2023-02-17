@file:Suppress("PropertyName")
@file:OptIn(ExperimentalSerializationApi::class)

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

sealed class UserNotification {

    @Serializable
    enum class Type(val value: String) {
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
        TaggedInNote("TaggedInNote");

        companion object {
            private val valueToType = Type.values().associateBy { it.value }
            fun lookup(value: String): Type = valueToType[value]!!
        }
    }

    @Serializable
    data class Network(
        val _id: String,
        val userId: String,
        val actionId: String,
        val type: String
    ) : UserNotification()

    @Serializable
    sealed class Output : UserNotification() {
        @Serializable
        data class Populated<out T : Any>(
            val id: String,
            val user: User.Output.Unpopulated,
            val action: UserAction.Output.Populated<T>,
            val type: Type
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val userId: String,
            val actionId: String,
            val type: Type
        ) : Output()
    }
}