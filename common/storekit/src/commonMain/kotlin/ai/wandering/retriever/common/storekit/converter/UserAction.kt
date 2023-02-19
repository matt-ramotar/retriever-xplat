package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalUserAction
import ai.wandering.retriever.common.storekit.entity.UserAction

fun UserAction.Network.asUnpopulatedOutput(): UserAction.Output.Unpopulated = UserAction.Output.Unpopulated(
    id = _id,
    userId = userId,
    objId = objectId,
    type = UserAction.Type.lookup(type)
)

fun LocalUserAction.asUnpopulatedOutput(): UserAction.Output.Unpopulated = UserAction.Output.Unpopulated(
    id = id,
    userId = userId,
    objId = objectId,
    type = type
)