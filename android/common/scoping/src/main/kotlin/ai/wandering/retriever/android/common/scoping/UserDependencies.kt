package ai.wandering.retriever.android.common.scoping

import com.squareup.anvil.annotations.ContributesTo
import ai.wandering.retriever.common.storekit.api.NotesApi
import ai.wandering.retriever.common.storekit.entities.user.output.User

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: User
    val api: NotesApi
}