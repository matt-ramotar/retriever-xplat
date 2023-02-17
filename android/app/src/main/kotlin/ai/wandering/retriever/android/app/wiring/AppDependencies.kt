package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import com.squareup.anvil.annotations.ContributesTo

@ContributesTo(AppScope::class)
interface AppDependencies {
    val authRepository: AuthRepository
    val api: RetrieverApi
    val database: RetrieverDatabase
    val socket: Socket
}