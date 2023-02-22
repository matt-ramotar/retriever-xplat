@file:Suppress("PropertyName")

package ai.wandering.retriever.common.storekit.entity

sealed interface Identifiable {
    interface Network : Identifiable {
        val _id: String
    }

    interface Output : Identifiable {
        val id: String
    }
}