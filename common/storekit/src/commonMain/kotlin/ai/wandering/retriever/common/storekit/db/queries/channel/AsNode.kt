package ai.wandering.retriever.common.storekit.db.queries.channel

import ai.wandering.retriever.common.storekit.LocalChannelQueries
import ai.wandering.retriever.common.storekit.entity.Channel
import kotlinx.datetime.Instant

fun LocalChannelQueries.asNode(id: String): Channel.Output.Node {
    val response = findByIdAsUnpopulated(id).executeAsList()

    val node = response.first()

    return Channel.Output.Node(
        id = node.id,
        userId = node.userId,
        graphId = node.graphId,
        tagId = node.tagId,
        createdAt = Instant.parse(node.createdAt)
    )
}