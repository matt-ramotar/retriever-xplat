package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalUser
import ai.wandering.retriever.common.storekit.entity.User

fun User.Network.asLocal(): LocalUser = LocalUser(
    id = _id,
    username = username,
    name = name,
    email = email,
    avatarUrl = avatarUrl,
    coverImageUrl = coverImageUrl,
    bio = bio
)