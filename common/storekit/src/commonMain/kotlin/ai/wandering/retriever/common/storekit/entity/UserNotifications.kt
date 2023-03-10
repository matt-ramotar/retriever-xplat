package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserNotifications(
    val notifications: List<UserNotification.Network>
)