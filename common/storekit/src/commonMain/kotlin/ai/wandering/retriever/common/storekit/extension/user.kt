package ai.wandering.retriever.common.storekit.extension

import ai.wandering.retriever.common.storekit.entity.user.network.NetworkUser
import ai.wandering.retriever.common.storekit.entity.user.output.User

fun NetworkUser.toUser() = User(
    id = id,
    username = username,
    name = name,
    email = email,
    avatarUrl = avatarUrl
)