package ai.wandering.retriever.common.storekit.entities.note

import ai.wandering.retriever.common.storekit.entities.user.output.User
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: String,
    val user: User,
    val content: String,
    val isRead: Boolean,
    val channels: List<Channel>,
    val mentions: List<Mention>,
    val parents: List<Relationship>,
    val references: List<Relationship>,
    val children: List<Relationship>
)

@Serializable
data class Tag(
    val id: String,
    val name: String
)

@Serializable
data class Channel(
    val id: String,
    val userId: String,
    val graphId: String,
    val tag: Tag
)


@Serializable
data class Mention(
    val userId: String,
    val otherUserId: String,
    val otherUser: User
)

@Serializable
data class Relationship(
    val noteId: String,
    val otherNoteId: String,
    val type: Type
) {
    enum class Type {
        ChildToParent,
        ParentToChild,
        UnidirectionalReference,
        BidirectionalReference
    }
}