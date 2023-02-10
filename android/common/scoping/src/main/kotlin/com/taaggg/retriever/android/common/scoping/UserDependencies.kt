package com.taaggg.retriever.android.common.scoping

import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.retriever.common.storekit.api.NotesApi
import com.taaggg.retriever.common.storekit.entities.user.output.User

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: User
    val api: NotesApi
}