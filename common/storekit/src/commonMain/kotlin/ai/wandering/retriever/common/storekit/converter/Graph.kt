package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.entity.Graph

fun Graph.Network.asNodeOutput() = Graph.Output.Node(
    id = _id,
    userId = userId,
    name = name
)