@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entities

import ai.wandering.retriever.common.storekit.entities.user.output.User
import kotlinx.serialization.Serializable

sealed class Thread {
    @Serializable
    data class Network(
        val _id: String,
        val title: String?,
        val description: String?,
        val userId: String,
    ) : Thread()

    @Serializable
    sealed class Output : Thread() {
        @Serializable
        data class Populated(
            val id: String,
            val title: String?,
            val description: String?,
            val User: User,
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val title: String?,
            val description: String?,
            val userId: String,
        ) : Output()
    }
}