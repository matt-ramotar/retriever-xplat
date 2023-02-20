package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalGraph
import ai.wandering.retriever.common.storekit.entity.Graph
import kotlinx.datetime.Instant

fun Graph.Network.asNodeOutput() = Graph.Output.Node(
    id = _id,
    userId = userId,
    name = name,
    createdAt = Instant.parse(createdAt)
)

fun Graph.Output.Node.asLocal() = LocalGraph(
    id = id,
    name = name,
    ownerId = userId,
    createdAt = createdAt.toString()
)