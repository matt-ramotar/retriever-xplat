package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalNote
import ai.wandering.retriever.common.storekit.entity.Note
import kotlinx.datetime.Instant

fun Note.Network.asNodeOutput() = Note.Output.Node(
    id = _id,
    userId = userId,
    content = content,
    isRead = is_read,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)

fun Note.Output.Node.asLocal() = LocalNote(
    id = id,
    userId = userId,
    content = content,
    is_read = isRead,
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString()
)