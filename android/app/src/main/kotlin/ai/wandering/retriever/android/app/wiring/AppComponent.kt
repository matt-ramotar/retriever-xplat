package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.app.RetrieverApp
import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: RetrieverApp,
            @BindsInstance database: RetrieverDatabase,
            @BindsInstance applicationContext: Context,
            @BindsInstance socket: Socket
        ): AppComponent
    }
}