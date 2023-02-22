@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

import kotlinx.serialization.Serializable

sealed class Thread {
    @Serializable
    data class Network(
        override val _id: String,
        val title: String?,
        val description: String?,
        val userId: String,
    ) : Thread(), Identifiable.Network

    @Serializable
    sealed class Output : Thread(), Identifiable.Output {
        @Serializable
        data class Populated(
            override val id: String,
            val title: String?,
            val description: String?,
            val User: User.Output.Node,
        ) : Output()

        @Serializable
        data class Unpopulated(
            override val id: String,
            val title: String?,
            val description: String?,
            val userId: String,
        ) : Output()
    }
}