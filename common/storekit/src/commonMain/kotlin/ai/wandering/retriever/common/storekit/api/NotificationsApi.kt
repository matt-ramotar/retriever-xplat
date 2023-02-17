package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.notifications.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationsApi {
    fun subscribeToNotifications(userId: String): Flow<List<Notification>>
}