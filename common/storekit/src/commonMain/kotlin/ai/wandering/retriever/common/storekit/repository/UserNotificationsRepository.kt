package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entity.UserNotification
import kotlinx.coroutines.flow.StateFlow


interface UserNotificationsRepository {
    val notifications: StateFlow<List<UserNotification.Output.Unpopulated>>
}
