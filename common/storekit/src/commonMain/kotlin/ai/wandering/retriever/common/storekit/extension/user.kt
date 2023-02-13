package ai.wandering.retriever.common.storekit.extension

import ai.wandering.retriever.common.storekit.entities.user.network.NetworkUser
import ai.wandering.retriever.common.storekit.entities.user.output.User

fun NetworkUser.toUser() = User(
    id = id,
    username = username,
    name = name,
    email = email,
    avatarUrl = avatarUrl
)