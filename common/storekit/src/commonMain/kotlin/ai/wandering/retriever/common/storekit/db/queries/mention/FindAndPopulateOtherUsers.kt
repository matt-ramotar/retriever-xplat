package ai.wandering.retriever.common.storekit.db.queries.mention

import ai.wandering.retriever.common.storekit.LocalMentionQueries
import ai.wandering.retriever.common.storekit.entity.User

// TODO(mramotar)
fun LocalMentionQueries.findAndPopulateOtherUsers(userId: String): List<User.Output.Populated> {
    val response = findAndPopulateOtherUsersByUserId(userId).executeAsList()
    return response
        .filter { row -> row.id != null && row.username != null && row.name != null && row.email != null }
        .map {
            User.Output.Populated(
                id = it.id!!,
                username = it.username!!,
                name = it.name!!,
                email = it.email!!,
                avatarUrl = it.avatarUrl,
                coverImageUrl = it.coverImageUrl,
                followedGraphs = listOf(),
                followedTags = listOf(),
                followedUsers = listOf(),
                followers = listOf(),
                pinnedNotes = listOf(),
                pinnedChannels = listOf(),
                pinnedGraphs = listOf(),
                notes = listOf(),
                userActions = listOf(),
                graphs = listOf()
            )
        }
}


