package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalUserNotification
import ai.wandering.retriever.common.storekit.entity.UserNotification

fun UserNotification.Network.asUnpopulatedOutput(): UserNotification.Output.Unpopulated = UserNotification.Output.Unpopulated(
    id = _id,
    userId = userId,
    actionId = actionId,
    type = UserNotification.Type.lookup(type)
)

fun UserNotification.Network.asLocal(): LocalUserNotification = LocalUserNotification(
    id = _id,
    userId = userId,
    actionId = actionId,
    type = UserNotification.Type.lookup(type)
)

fun LocalUserNotification.asUnpopulatedOutput(): UserNotification.Output.Unpopulated = UserNotification.Output.Unpopulated(
    id = id,
    userId = userId,
    actionId = actionId,
    type = type
)

fun UserNotification.Output.Unpopulated.asLocal(): LocalUserNotification = LocalUserNotification(
    id = id,
    userId = userId,
    actionId = actionId,
    type = type
)