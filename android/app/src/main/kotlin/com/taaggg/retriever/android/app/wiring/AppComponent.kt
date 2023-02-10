package com.taaggg.retriever.android.app.wiring

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import com.taaggg.retriever.android.app.RetrieverApp
import com.taaggg.retriever.android.common.scoping.AppScope
import com.taaggg.retriever.android.common.scoping.SingleIn
import dagger.BindsInstance
import dagger.Component

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: RetrieverApp,
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}