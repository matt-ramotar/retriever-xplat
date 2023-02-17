package ai.wandering.retriever.android.app.auth

import ai.wandering.retriever.common.storekit.entity.user.output.User
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassableUser(
    val id: String,
    val username: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null
) : Parcelable

fun User.parcelize() = PassableUser(id, username, name, email, avatarUrl)
fun PassableUser.deparcelize() = User(id, username, name, email, avatarUrl)