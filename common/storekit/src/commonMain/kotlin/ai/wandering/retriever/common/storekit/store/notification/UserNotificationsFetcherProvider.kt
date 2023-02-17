package ai.wandering.retriever.common.storekit.store.notification

import ai.wandering.retriever.common.storekit.api.UserNotificationsApi
import ai.wandering.retriever.common.storekit.entity.UserNotification
import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher


class UserNotificationsFetcherProvider(private val api: UserNotificationsApi) {
    fun provide(): Fetcher<String, List<UserNotification.Network>> = Fetcher.ofFlow { userId ->
        api.subscribe(userId).map { result ->
            when (result) {
                is RequestResult.Exception -> {
                    listOf()
                }

                is RequestResult.Success -> {
                    result.data.notifications
                }
            }
        }
    }
}