package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalUserAction
import ai.wandering.retriever.common.storekit.entity.Identifiable
import ai.wandering.retriever.common.storekit.entity.UserAction

fun UserAction.Network.Unpopulated.asUnpopulatedOutput(): UserAction.Output.Unpopulated = UserAction.Output.Unpopulated(
    id = _id,
    userId = userId,
    objId = objectId,
    model = UserAction.Model.lookup(model),
    type = UserAction.Type.lookup(type)
)

fun LocalUserAction.asUnpopulatedOutput(): UserAction.Output.Unpopulated = UserAction.Output.Unpopulated(
    id = id,
    userId = userId,
    objId = objectId,
    model = model,
    type = type
)

fun UserAction.Output.Populated<*>.asLocal(): LocalUserAction {
    val objectId = when (obj) {
        is Identifiable.Network -> obj._id
        is Identifiable.Output -> obj.id
    }

    return LocalUserAction(
        id = id,
        userId = user.id,
        objectId = objectId,
        model = model,
        type = type
    )
}
