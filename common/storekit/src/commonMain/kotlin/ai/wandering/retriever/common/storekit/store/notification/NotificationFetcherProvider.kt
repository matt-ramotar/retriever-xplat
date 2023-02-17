package ai.wandering.retriever.common.storekit.store.notification

import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.entities.UserNotification
import org.mobilenativefoundation.store.store5.Fetcher


class NotificationFetcherProvider(private val api: RetrieverApi) {
    fun provide(): Fetcher<String, List<UserNotification>> = Fetcher.ofFlow { userId ->
        api.subscribeToNotifications(userId)
    }
}