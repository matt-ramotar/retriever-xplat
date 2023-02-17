package ai.wandering.retriever.common.notifications

import kotlinx.serialization.Serializable

@Serializable
data class Notifications(
    val notifications: List<Notification>
)

