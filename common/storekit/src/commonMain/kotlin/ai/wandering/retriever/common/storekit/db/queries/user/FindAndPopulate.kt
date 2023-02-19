package ai.wandering.retriever.common.storekit.db.queries.user

import ai.wandering.retriever.common.storekit.LocalUserQueries
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.Graph
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.entity.Tag
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.entity.UserAction
import kotlinx.datetime.Instant

fun LocalUserQueries.findAndPopulate(userId: String): User.Output.Populated {
    val userResponse = findByIdAndPopulateAll(userId).executeAsList()

    val common = userResponse.first()

    val followed = userResponse
        .filter { row -> row.followedUserId != null }
        .map { row ->
            User.Output.Node(
                id = row.followedUserId!!,
                username = row.followedUsername!!,
                name = row.followedName!!,
                email = row.followedEmail!!,
                avatarUrl = row.followedAvatarUrl,
                coverImageUrl = row.followedCoverImageUrl,
                bio = row.followedBio,
            )
        }
        .distinct()

    val userActionsResponse = findUserActionsByUserId(listOf(userId)).executeAsList()

    val userActions = userActionsResponse.map { row -> UserAction.Output.Node(row.userId, row.objectId, row.objectId, row.type) }

    val followers = userResponse
        .filter { row -> row.followerId != null }
        .map { row ->
            User.Output.Node(
                id = row.followerId!!,
                username = row.followerUsername!!,
                name = row.followerName!!,
                email = row.followerEmail!!,
                avatarUrl = row.followerAvatarUrl,
                coverImageUrl = row.followerCoverImageUrl,
                bio = row.followerBio,
            )
        }
        .distinct()

    val notes = userResponse
        .filter { row -> row.noteId != null }
        .map { row -> Note.Output.Node(row.noteId!!, row.id, row.noteContent!!, row.noteIsRead!!, Instant.parse(row.noteCreatedAt!!), Instant.parse(row.noteUpdatedAt!!)) }
        .distinct()

    val pinnedNotes = userResponse
        .filter { row -> row.pinnedNoteId != null }
        .map { row ->
            Note.Output.Node(
                row.pinnedNoteId!!,
                row.id,
                row.pinnedNoteContent!!,
                row.pinnedNoteIsRead!!,
                Instant.parse(row.pinnedNoteCreatedAt!!),
                Instant.parse(row.pinnedNoteUpdatedAt!!)
            )
        }
        .distinct()

    val graphs = userResponse
        .filter { row -> row.graphId != null }
        .map { row -> Graph.Output.Node(row.graphId!!, row.graphName!!, row.graphOwnerId!!) }
        .distinct()
    val followedTags = userResponse
        .filter { row -> row.followedTagId != null }
        .map { row -> Tag.Output.Node(row.followedTagId!!, row.followedTagName!!) }
        .distinct()
    val followedGraphs = userResponse
        .filter { row -> row.followedGraphId != null }
        .map { row -> Graph.Output.Node(row.followedGraphId!!, row.followedGraphName!!, row.followedGraphOwnerId!!) }
        .distinct()
    val pinnedGraphs = userResponse
        .filter { row -> row.pinnedGraphId != null }
        .map { row -> Graph.Output.Node(row.pinnedGraphId!!, row.pinnedGraphName!!, row.pinnedGraphOwnerId!!) }
        .distinct()
    val pinnedChannels = userResponse.filter { row -> row.pinnedChannelId != null }
        .map { row -> Channel.Output.Node(row.pinnedChannelId!!, Instant.parse(row.pinnedChannelCreatedAt!!)) }
        .distinct()

    return User.Output.Populated(
        id = common.id,
        username = common.username,
        name = common.name,
        email = common.email,
        avatarUrl = common.avatarUrl,
        coverImageUrl = common.coverImageUrl,
        bio = common.bio,
        followedUsers = followed,
        followers = followers,
        graphs = graphs,
        pinnedGraphs = pinnedGraphs,
        followedGraphs = followedGraphs,
        pinnedChannels = pinnedChannels,
        followedTags = followedTags,
        notes = notes,
        pinnedNotes = pinnedNotes,
        userActions = userActions
    )
}
