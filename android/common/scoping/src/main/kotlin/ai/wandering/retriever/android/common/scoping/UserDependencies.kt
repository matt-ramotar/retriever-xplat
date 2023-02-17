package ai.wandering.retriever.android.common.scoping

import ai.wandering.retriever.common.notifications.NotificationManager
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.entities.user.output.User
import com.squareup.anvil.annotations.ContributesTo

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: User
    val api: RetrieverApi
    val notificationManager: NotificationManager
}