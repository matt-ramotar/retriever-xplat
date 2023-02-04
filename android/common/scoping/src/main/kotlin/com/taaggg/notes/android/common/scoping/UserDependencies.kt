package com.taaggg.notes.android.common.scoping

import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.notes.common.storekit.api.NotesApi
import com.taaggg.notes.common.storekit.entities.user.output.User

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: User
    val api: NotesApi
}