package com.taaggg.retriever.common.storekit.entities.auth

import com.taaggg.retriever.common.storekit.entities.user.network.NetworkUser
import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthResponse(
    val user: NetworkUser,
    val token: String
)