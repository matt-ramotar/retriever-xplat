package ai.wandering.retriever.common.storekit.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserNotifications(
    val notifications: List<UserNotification.Network>
)