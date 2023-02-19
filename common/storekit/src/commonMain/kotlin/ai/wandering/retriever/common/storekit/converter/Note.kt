package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.entity.Note
import kotlinx.datetime.Instant

fun Note.Network.asNodeOutput() = Note.Output.Node(
    id = _id,
    userId = userId,
    content = content,
    isRead = isRead,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)