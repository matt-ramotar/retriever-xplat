package com.taaggg.retriever.common.storekit.entities.note

import com.taaggg.retriever.common.storekit.entities.user.output.User


data class Note(
    val id: String,
    val user: User,
    val content: String,
    val isRead: Boolean,
    val tags: List<Tag>,
    val mentions: List<Mention>,
    val parents: List<Relationship>,
    val references: List<Relationship>,
    val children: List<Relationship>
)

data class Tag(
    val id: String,
    val name: String
)

data class Mention(
    val userId: String,
    val otherUserId: String,
    val otherUser: User
)

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