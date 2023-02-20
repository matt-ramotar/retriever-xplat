package ai.wandering.retriever.common.storekit.db.queries.channel

import ai.wandering.retriever.common.storekit.LocalChannelQueries
import ai.wandering.retriever.common.storekit.LocalChannelsQueries
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.Graph
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.entity.Tag
import ai.wandering.retriever.common.storekit.entity.User
import kotlinx.datetime.Instant


fun LocalChannelsQueries.asPopulated(userId: String, channelQueries: LocalChannelQueries): List<Channel.Output.Populated> = try {

    println("userId: $userId")
    println("63e5a4bfe9bbc74b66f03de7")
    val channels = findById(userId).executeAsOneOrNull()
    println("Channels = $channels")

    val populated = channels?.channelIds?.mapNotNull { channelId ->
        try {
            val populated = channelQueries.asPopulated(channelId)
            println("Populated: $populated")
            populated
        } catch (error: Throwable) {
            println("Error populating: ${error.cause}")
            null
        }
    }
    println("Populated = $populated")
    populated ?: listOf()
} catch (error: Throwable) {
    println(error.cause)
    TODO()
}


fun LocalChannelQueries.asPopulated(id: String): Channel.Output.Populated {
    val response = findByIdAsPopulated(id).executeAsList()

    println("Local Channel Queries: $response")
    val node = response.first()

    try {


        val user = User.Output.Node(
            id = node.userId!!,
            name = node.userName!!,
            username = node.userUsername!!,
            email = node.userEmail!!,
            bio = node.userBio,
            avatarUrl = node.userAvatarUrl,
            coverImageUrl = node.userCoverImageUrl
        )

        val graph = Graph.Output.Node(
            id = node.graphId!!,
            userId = node.graphOwnerId!!,
            name = node.graphName!!,
            createdAt = Instant.parse(node.graphCreatedAt!!)
        )

        val tag = Tag.Output.Node(
            id = node.tagId!!,
            name = node.tagName!!
        )

        val notes = response.filter { it.noteId != null }
            .map {
                Note.Output.Node(
                    id = it.noteId!!,
                    userId = it.noteUserId!!,
                    content = it.noteContent!!,
                    isRead = it.noteIsRead!!,
                    createdAt = Instant.parse(it.noteCreatedAt!!),
                    updatedAt = Instant.parse(it.noteUpdatedAt!!)
                )
            }

        val pinners = response.filter { it.pinnerId != null }
            .map {
                User.Output.Node(
                    id = it.noteId!!,
                    name = it.pinnerName!!,
                    username = it.pinnerUsername!!,
                    email = it.pinnerEmail!!,
                    bio = it.pinnerBio,
                    avatarUrl = it.pinnerAvatarUrl,
                    coverImageUrl = it.pinnerCoverImageUrl
                )
            }

        return Channel.Output.Populated(
            id = node.id,
            createdAt = Instant.parse(node.createdAt),
            user = user,
            graph = graph,
            tag = tag,
            notes = notes,
            pinners = pinners
        )
    } catch (error: Throwable) {

        TODO()
    }
}
