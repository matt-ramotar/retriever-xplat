package com.taaggg.retriever.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.retriever.android.common.scoping.AppScope
import com.taaggg.retriever.common.storekit.RetrieverDatabase
import com.taaggg.retriever.common.storekit.api.NotesApi
import com.taaggg.retriever.common.storekit.repository.AuthRepository

@ContributesTo(AppScope::class)
interface AppDependencies {
    val authRepository: AuthRepository
    val api: NotesApi
    val database: RetrieverDatabase
}