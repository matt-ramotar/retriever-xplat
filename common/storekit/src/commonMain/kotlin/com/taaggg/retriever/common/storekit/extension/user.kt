package com.taaggg.retriever.common.storekit.extension

import com.taaggg.retriever.common.storekit.entities.user.network.NetworkUser
import com.taaggg.retriever.common.storekit.entities.user.output.User

fun NetworkUser.toUser() = User(
    id = id,
    name = name,
    email = email,
    avatarUrl = avatarUrl
)