package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entities.UserNotification
import kotlinx.coroutines.flow.Flow

interface NotificationManager {
    fun subscribeToNotifications(userId: String): Flow<List<UserNotification>>
}