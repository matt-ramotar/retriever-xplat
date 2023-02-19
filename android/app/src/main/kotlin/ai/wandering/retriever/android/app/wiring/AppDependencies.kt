package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.repository.auth.AuthRepository
import com.squareup.anvil.annotations.ContributesTo
import kotlinx.serialization.json.Json

@ContributesTo(AppScope::class)
interface AppDependencies {
    val api: RetrieverApi
    val serializer : Json
    val database: RetrieverDatabase
    val authRepository: AuthRepository
}