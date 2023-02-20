package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.entity.Mention

fun Mention.Network.asNodeOutput() = Mention.Output.Node(
    id = _id,
    userId = userId,
    otherUserId = otherUserId
)