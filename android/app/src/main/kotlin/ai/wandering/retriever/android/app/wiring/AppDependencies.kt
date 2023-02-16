package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.android.common.socket.RetrieverSocket
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.repository.AuthRepository
import com.squareup.anvil.annotations.ContributesTo

@ContributesTo(AppScope::class)
interface AppDependencies {
    val authRepository: AuthRepository
    val api: RetrieverApi
    val database: RetrieverDatabase
    val socket: RetrieverSocket
}