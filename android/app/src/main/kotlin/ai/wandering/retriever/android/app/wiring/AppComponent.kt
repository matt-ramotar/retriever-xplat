package ai.wandering.retriever.android.app.wiring

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import ai.wandering.retriever.android.app.RetrieverApp
import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.common.storekit.RetrieverDatabase
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
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}