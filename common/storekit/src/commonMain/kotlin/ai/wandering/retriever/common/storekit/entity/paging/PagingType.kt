package ai.wandering.retriever.common.storekit.entity.paging

import kotlinx.serialization.Serializable

@Serializable
enum class PagingType(val value: String) {
    Append("Append"),
    Prepend("Prepend"),
    Refresh("Refresh");

    companion object {
        private val values = values().associateBy { it.value }
        private fun lookup(value: String) = values[value]!!
    }
}