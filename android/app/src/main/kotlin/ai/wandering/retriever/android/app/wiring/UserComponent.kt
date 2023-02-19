package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.android.common.scoping.UserScope
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.BindsInstance

@SingleIn(UserScope::class)
@ContributesSubcomponent(scope = UserScope::class, parentScope = AppScope::class)
interface UserComponent {
    @ContributesSubcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance user: AuthenticatedUser
        ): UserComponent
    }

    @ContributesTo(AppScope::class)
    interface ParentBindings {
        fun userComponentFactory(): Factory
    }
}