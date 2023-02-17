@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

sealed class Tag {
    @Serializable
    data class Network(
        val _id: String,
        val name: String,
        val followerIds: String,
    ) : Tag()

    @Serializable
    sealed class Output : Tag() {
        @Serializable
        data class Populated(
            val id: String,
            val name: String,
            val followers: List<String>,
        ) : Output()

        @Serializable
        data class Unpopulated(
            val id: String,
            val name: String,
            val followerIds: List<String>,
        ) : Output()
    }
}