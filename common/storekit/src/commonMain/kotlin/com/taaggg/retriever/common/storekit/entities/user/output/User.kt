package com.taaggg.retriever.common.storekit.entities.user.output

import com.taaggg.retriever.common.storekit.entities.user.UserId
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val avatarUrl: String? = null
)