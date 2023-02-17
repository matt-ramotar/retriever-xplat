package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entity.UserNotifications
import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.coroutines.flow.Flow

interface UserNotificationsApi {
    fun subscribe(userId: String): Flow<RequestResult<UserNotifications>>
}