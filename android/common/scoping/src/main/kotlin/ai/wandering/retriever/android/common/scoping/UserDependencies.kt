package ai.wandering.retriever.android.common.scoping

import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.repository.ChannelsManager
import ai.wandering.retriever.common.storekit.repository.NoteRepository
import ai.wandering.retriever.common.storekit.repository.UserNotificationsRepository
import ai.wandering.retriever.common.storekit.store.UserActionPagingStore
import com.squareup.anvil.annotations.ContributesTo

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: AuthenticatedUser
    val api: RetrieverApi
    val userNotificationsRepository: UserNotificationsRepository
    val channelsManager: ChannelsManager
    val noteRepository: NoteRepository
}