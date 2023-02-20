package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalNote
import ai.wandering.retriever.common.storekit.entity.Note
import kotlinx.datetime.Instant

fun Note.Network.Unpopulated.asNodeOutput() = Note.Output.Node(
    id = _id,
    userId = userId,
    content = content,
    isRead = is_read,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)

fun Note.Network.Populated.asNodeOutput() = Note.Output.Node(
    id = _id,
    userId = user._id,
    content = content,
    isRead = is_read,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)

fun Note.Network.Node.asNodeOutput() = Note.Output.Node(
    id = _id,
    userId = userId,
    content = content,
    isRead = is_read,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)


fun Note.Network.Populated.asPopulatedOutput() = Note.Output.Populated.Created(
    id = _id,
    user = user.asNodeOutput(),
    content = content,
    isRead = is_read,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
    channels = channels.map { it.asNodeOutput() },
    mentions = mentions.map { it.asNodeOutput() },
    noteRelationships = noteRelationships.map { it.asNodeOutput() },
    threadNotes = threadNotes.map { it.asNodeOutput() },
    pinners = pinners.map { it.asNodeOutput() }
)

fun Note.Output.Populated.Created.asLocal() = LocalNote(
    id = id,
    userId = user.id,
    content = content,
    is_read = isRead,
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString()
)


fun Note.Output.Node.asLocal() = LocalNote(
    id = id,
    userId = userId,
    content = content,
    is_read = isRead,
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString()
)

fun Note.Output.Populated.Created.asNodeOutput() = Note.Output.Node(
    id = id,
    userId = user.id,
    content = content,
    isRead = isRead,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Note.Output.Populated.Created.asUnpopulatedOutput() = Note.Output.Unpopulated(
    id = id,
    userId = user.id,
    content = content,
    isRead = isRead,
    createdAt = createdAt,
    updatedAt = updatedAt,
    channelIds = channels.map { it.id },
    mentionIds = mentions.map { it.id },
    noteRelationshipIds = noteRelationships.map { it.id },
    threadNoteIds = threadNotes.map { it.id },
    pinnerIds = pinners.map { it.id }

)

fun Note.Output.Populated.asLocal() = when (this) {
    is Note.Output.Populated.Created -> asLocal()
    is Note.Output.Populated.Draft -> asLocal()
}

fun Note.Output.Populated.Draft.asLocal() = LocalNote(
    id = id,
    userId = user.id,
    content = content,
    is_read = isRead,
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString()
)

fun Note.Output.Populated.asUnpopulatedOutput() = when (this) {
    is Note.Output.Populated.Created -> asUnpopulatedOutput()
    is Note.Output.Populated.Draft -> asUnpopulatedOutput()
}

fun Note.Output.Populated.Draft.asUnpopulatedOutput() = Note.Output.Unpopulated(
    id = id,
    userId = user.id,
    content = content,
    isRead = isRead,
    createdAt = createdAt,
    updatedAt = updatedAt,
    channelIds = channels.map { it.id },
    mentionIds = mentions.map { it.id },
    noteRelationshipIds = noteRelationships.map { it.id },
    threadNoteIds = threadNotes.map { it.id },
    pinnerIds = pinners.map { it.id }
)