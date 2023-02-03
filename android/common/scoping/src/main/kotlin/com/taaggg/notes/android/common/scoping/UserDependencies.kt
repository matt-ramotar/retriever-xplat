package com.taaggg.notes.android.common.scoping

import com.squareup.anvil.annotations.ContributesTo
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: AuthenticatedHowlUser
    val howlerApi: HowlerApi
}