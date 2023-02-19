package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.android.common.scoping.UserScope
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.repository.UserNotificationsRepository
import ai.wandering.retriever.common.storekit.repository.impl.RealUserNotificationsRepository
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides


@Module
@ContributesTo(UserScope::class)
object UserModule {
    @SingleIn(UserScope::class)
    @Provides
    fun provideUserNotificationsRepository(
        socket: UserNotificationsSocketApi,
        user: AuthenticatedUser
    ): UserNotificationsRepository = RealUserNotificationsRepository(socket, user)
}