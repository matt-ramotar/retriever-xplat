package ai.wandering.retriever.common.storekit.repository.impl

import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.converter.asUnpopulatedOutput
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.entity.UserNotification
import ai.wandering.retriever.common.storekit.repository.UserNotificationsRepository
import ai.wandering.retriever.common.storekit.result.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RealUserNotificationsRepository(socket: UserNotificationsSocketApi, user: AuthenticatedUser) : UserNotificationsRepository {
    private val _notifications = MutableStateFlow<List<UserNotification.Output.Unpopulated>>(listOf())
    override val notifications: StateFlow<List<UserNotification.Output.Unpopulated>> = _notifications

    init {
        CoroutineScope(Dispatchers.Default).launch {
            socket.subscribe(user.id).collectLatest { result ->
                when (result) {
                    is RequestResult.Exception -> {
                    }

                    is RequestResult.Success -> {
                        _notifications.value = result.data.notifications.map { it.asUnpopulatedOutput() }
                    }
                }
            }
        }
    }
}
