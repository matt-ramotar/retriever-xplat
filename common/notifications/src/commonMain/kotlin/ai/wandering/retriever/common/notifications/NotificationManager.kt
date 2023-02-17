package ai.wandering.retriever.common.notifications

import kotlinx.coroutines.flow.StateFlow

interface NotificationManager {
    val notifications: StateFlow<List<Notification>>
}