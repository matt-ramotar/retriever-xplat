package com.taaggg.notes.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.notes.android.common.scoping.AppScope
import com.taaggg.notes.common.storekit.api.NotesApi
import com.taaggg.notes.common.storekit.repository.AuthRepository

@ContributesTo(AppScope::class)
interface AppDependencies {
    val authRepository: AuthRepository
    val api: NotesApi
}