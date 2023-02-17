package ai.wandering.retriever.common.storekit.store.notification

import ai.wandering.retriever.common.notifications.Notification
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import org.mobilenativefoundation.store.store5.Fetcher


class NotificationFetcherProvider(private val api: RetrieverApi) {
    fun provide(): Fetcher<String, List<Notification>> = Fetcher.ofFlow { userId ->
        api.subscribeToNotifications(userId)
    }
}