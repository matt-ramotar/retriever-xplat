package ai.wandering.retriever.common.storekit.db.queries.channel

import ai.wandering.retriever.common.storekit.LocalChannelQueries
import ai.wandering.retriever.common.storekit.entity.Channel
import kotlinx.datetime.Instant

fun LocalChannelQueries.asUnpopulated(id: String): Channel.Output.Unpopulated {
    val response = findByIdAsUnpopulated(id).executeAsList()

    val node = response.first()
    val pinnerIds = response.filter { it.pinnerId != null }.map { it.pinnerId!! }.distinct()
    val noteIds = response.filter { it.noteId != null }.map { it.noteId!! }.distinct()

    return Channel.Output.Unpopulated(
        id = node.id,
        userId = node.userId,
        graphId = node.graphId,
        tagId = node.tagId,
        noteIds = noteIds,
        pinnerIds = pinnerIds,
        createdAt = Instant.parse(node.createdAt)
    )
}
