package com.taaggg.notes.android.app.wiring

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import com.taaggg.notes.android.app.NotesApp
import com.taaggg.notes.android.common.scoping.AppScope
import com.taaggg.notes.android.common.scoping.SingleIn
import dagger.BindsInstance
import dagger.Component

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: NotesApp,
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}