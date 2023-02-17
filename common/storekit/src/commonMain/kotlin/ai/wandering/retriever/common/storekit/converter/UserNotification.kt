package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.entity.UserNotification

fun UserNotification.Network.toUnpopulatedOutput(): UserNotification.Output.Unpopulated = UserNotification.Output.Unpopulated(
    id = _id,
    actionId = actionId,
    type = UserNotification.Type.lookup(type)
)