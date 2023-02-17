package ai.wandering.retriever.android.app

import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.android.common.scoping.UserScope
import ai.wandering.retriever.common.notifications.Notification
import ai.wandering.retriever.common.notifications.NotificationManager
import ai.wandering.retriever.common.storekit.entity.user.output.User
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@SingleIn(UserScope::class)
@ContributesBinding(UserScope::class)
class RetrieverNotificationManager @Inject constructor(api: RetrieverApi, user: User) : NotificationManager {
    private val _notifications = MutableStateFlow<List<Notification>>(listOf())
    override val notifications: StateFlow<List<Notification>> = _notifications

    init {
        CoroutineScope(Dispatchers.IO).launch {
            api.subscribe(user.id).collectLatest { _notifications.value = it }
        }
    }

}