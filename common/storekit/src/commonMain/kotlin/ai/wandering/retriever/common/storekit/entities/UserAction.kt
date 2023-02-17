@file:Suppress("PropertyName")
@file:OptIn(ExperimentalSerializationApi::class)

package ai.wandering.retriever.common.storekit.entities

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed class UserAction {

    @Serializable
    enum class Type(val value: String) {
        @JsonNames(
            "CreateNote",
            "CreateThread",
            "CreateChannel",
            "CreateGraph",
            "CommentOnNote",
            "CommentOnThread",
            "SubscribeToNote",
            "SubscribeToThread",
            "SubscribeToChannel",
            "SubscribeToGraph",
            "SubscribeToUser",
            "PinNote",
            "PinThread",
            "PinChannel",
            "PinGraph",
            "StarChannel",
            "StarGraph",
            "UpvoteNote",
            "UpvoteThread",
            "ReactToNote",
            "ReactToThread",
            "TaggedInNote"
        )
        CreateNote("CreateNote"),
        CreateThread("CreateThread"),
        CreateChannel("CreateChannel"),
        CreateGraph("CreateGraph"),
        CommentOnNote("CommentOnNote"),
        CommentOnThread("CommentOnThread"),
        SubscribeToNote("SubscribeToNote"),
        SubscribeToThread("SubscribeToThread"),
        SubscribeToChannel("SubscribeToChannel"),
        SubscribeToGraph("SubscribeToGraph"),
        SubscribeToUser("SubscribeToUser"),
        PinNote("PinNote"),
        PinThread("PinThread"),
        PinChannel("PinChannel"),
        PinGraph("PinGraph"),
        StarChannel("StarChannel"),
        StarGraph("StarGraph"),
        UpvoteNote("UpvoteNote"),
        UpvoteThread("UpvoteThread"),
        ReactToNote("ReactToNote"),
        ReactToThread("ReactToThread"),
        TaggedInNote("TaggedInNote"),
    }

    @Serializable
    data class Network(
        val _id: String,
        val userId: String,
        val objectId: String,
        val type: String
    ) : UserAction()

    @Serializable
    sealed class Output : UserAction() {
        @Serializable
        data class Populated<out T : Any>(
            val id: String,
            val user: User.Output.Unpopulated,
            val obj: T,
            val type: Type
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val userId: String,
            val objId: String,
            val type: Type
        ) : Output()
    }
}