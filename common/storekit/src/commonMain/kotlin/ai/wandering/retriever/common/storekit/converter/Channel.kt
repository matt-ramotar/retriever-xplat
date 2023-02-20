package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalChannel
import ai.wandering.retriever.common.storekit.LocalChannels
import ai.wandering.retriever.common.storekit.entity.Channel
import kotlinx.datetime.Instant

fun Channel.Network.Unpopulated.asUnpopulatedOutput() = Channel.Output.Unpopulated(
    id = _id,
    userId = userId,
    graphId = graphId,
    tagId = tagId,
    noteIds = noteIds,
    pinnerIds = pinnerIds,
    createdAt = Instant.parse(createdAt)
)

fun List<Channel.Output.Unpopulated>.asLocal() = LocalChannels(
    userId = first().userId,
    channelIds = map { it.id }
)

fun Channel.Output.Unpopulated.asLocal() = LocalChannel(
    id = id,
    userId = userId,
    graphId = graphId,
    tagId = tagId,
    createdAt = createdAt.toString()
)

fun LocalChannel.asUnpopulatedOutput(noteIds: List<String>, pinnerIds: List<String>) = Channel.Output.Unpopulated(
    id = id,
    userId = userId,
    graphId = graphId,
    tagId = tagId,
    noteIds = noteIds,
    pinnerIds = pinnerIds,
    createdAt = Instant.parse(createdAt)
)

fun Channel.Network.Populated.asUnpopulatedOutput() = Channel.Output.Unpopulated(
    id = _id,
    createdAt = createdAt,
    userId = user._id,
    graphId = graph._id,
    tagId = tag._id,
    noteIds = notes.map { it._id },
    pinnerIds = pinners.map { it._id }
)

fun Channel.Output.Unpopulated.asNodeOutput() = Channel.Output.Node(
    id = id,
    userId = userId,
    graphId = graphId,
    tagId = tagId,
    createdAt = createdAt
)

fun Channel.Network.Populated.asPopulatedOutput() = Channel.Output.Populated(
    id = _id,
    createdAt = createdAt,
    user = user.asNodeOutput(),
    graph = graph.asNodeOutput(),
    tag = tag.asNodeOutput(),
    notes = notes.map { it.asNodeOutput() },
    pinners = pinners.map { it.asNodeOutput() }
)

fun Channel.Network.Node.asNodeOutput() = Channel.Output.Node(
    id = _id,
    userId = userId,
    graphId = graphId,
    tagId = tagId,
    createdAt = createdAt
)


fun Channel.Output.Populated.asLocal() = LocalChannel(
    id = id,
    userId = user.id,
    graphId = graph.id,
    tagId = tag.id,
    createdAt = createdAt.toString()
)

fun Channel.Output.Populated.asNodeOutput() = Channel.Output.Node(
    id = id,
    userId = user.id,
    graphId = graph.id,
    tagId = tag.id,
    createdAt = createdAt
)