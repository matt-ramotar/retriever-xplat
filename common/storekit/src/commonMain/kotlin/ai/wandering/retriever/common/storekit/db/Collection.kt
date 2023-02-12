package ai.wandering.retriever.common.storekit.db

interface Collection<Output : Any> {
    fun find(): List<Output>
    fun findById(id: String): Output?
    fun find(tag: String): List<Output>
}