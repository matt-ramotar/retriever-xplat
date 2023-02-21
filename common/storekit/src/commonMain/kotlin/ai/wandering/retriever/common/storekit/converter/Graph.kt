package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalGraph
import ai.wandering.retriever.common.storekit.entity.Graph
import kotlinx.datetime.Instant

fun Graph.Network.Unpopulated.asNodeOutput() = Graph.Output.Node(
    id = _id,
    userId = userId,
    name = name,
    createdAt = Instant.parse(createdAt)
)


fun Graph.Network.Populated.asPopulatedOutput() = Graph.Output.Populated(
    id = _id,
    user = user.asNodeOutput(),
    name = name,
    createdAt = Instant.parse(createdAt),
    followers = followers.map { it.asNodeOutput() },
    pinners = pinners.map { it.asNodeOutput() },
)


fun Graph.Output.Node.asLocal() = LocalGraph(
    id = id,
    name = name,
    ownerId = userId,
    createdAt = createdAt.toString()
)

fun Graph.Network.Node.asNodeOutput() = Graph.Output.Node(
    id = _id,
    userId = userId,
    name = name,
    createdAt = Instant.parse(createdAt)
)