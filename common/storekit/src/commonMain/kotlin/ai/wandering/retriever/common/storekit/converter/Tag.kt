package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.entity.Tag

fun Tag.Network.asNodeOutput() = Tag.Output.Node(
    id = _id,
    name = name
)