@file:Suppress("PropertyName")
@file:OptIn(ExperimentalSerializationApi::class)

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

sealed class UserAction {

    @Serializable
    enum class Model(val value: String) {
        Note("Note"),
        Thread("Thread"),
        Channel("Channel"),
        Graph("Graph"),
        User("User");

        companion object {
            private val valueToModel = Model.values().associateBy { it.value }
            fun lookup(value: String): Model = valueToModel[value]!!
        }
    }

    @Serializable
    enum class Type(val value: String) {
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
        TaggedInNote("TaggedInNote");

        companion object {
            private val valueToType = Type.values().associateBy { it.value }
            fun lookup(value: String): Type = valueToType[value]!!
        }
    }

    @Serializable
    sealed class Network : UserAction() {

        @Serializable
        data class Populated(
            val _id: String,
            val user: User.Network,
            val obj: String,
            val model: String,
            val type: String
        ) : Network()

        @Serializable
        data class Unpopulated(
            val _id: String,
            val userId: String,
            val objectId: String,
            val model: String,
            val type: String
        ) : Network()
    }

    @Serializable
    sealed class Output : UserAction() {
        @Serializable
        data class Populated<out T : Identifiable>(
            val id: String,
            val user: User.Output.Node,
            val obj: T,
            val model: Model,
            val type: Type
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val userId: String,
            val objId: String,
            val model: Model,
            val type: Type
        ) : Output()

        @Serializable
        data class Node(
            val id: String,
            val userId: String,
            val objId: String,
            val model: Model,
            val type: Type
        ) : Output()
    }
}