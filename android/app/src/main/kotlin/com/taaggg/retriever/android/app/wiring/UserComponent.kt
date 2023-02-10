package com.taaggg.retriever.android.app.wiring

import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.retriever.android.common.scoping.AppScope
import com.taaggg.retriever.android.common.scoping.SingleIn
import com.taaggg.retriever.android.common.scoping.UserScope
import com.taaggg.retriever.common.storekit.entities.user.output.User
import dagger.BindsInstance

@SingleIn(UserScope::class)
@ContributesSubcomponent(scope = UserScope::class, parentScope = AppScope::class)
interface UserComponent {
    @ContributesSubcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance user: User
        ): UserComponent
    }

    @ContributesTo(AppScope::class)
    interface ParentBindings {
        fun userComponentFactory(): Factory
    }
}