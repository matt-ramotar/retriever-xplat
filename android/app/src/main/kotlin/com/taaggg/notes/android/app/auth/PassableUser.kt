package com.taaggg.notes.android.app.auth

import android.os.Parcelable
import com.taaggg.notes.common.storekit.entities.user.output.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassableUser(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null
) : Parcelable

fun User.parcelize() = PassableUser(id, name, email, avatarUrl)