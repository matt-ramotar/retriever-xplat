package ai.wandering.retriever.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.NotesApi
import ai.wandering.retriever.common.storekit.repository.AuthRepository

@ContributesTo(AppScope::class)
interface AppDependencies {
    val authRepository: AuthRepository
    val api: NotesApi
    val database: RetrieverDatabase
}