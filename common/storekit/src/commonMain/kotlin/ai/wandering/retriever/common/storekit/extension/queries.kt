package ai.wandering.retriever.common.storekit.extension

import ai.wandering.retriever.common.storekit.LocalMentionQueries
import ai.wandering.retriever.common.storekit.LocalNoteQueries
import ai.wandering.retriever.common.storekit.LocalTagQueries
import ai.wandering.retriever.common.storekit.LocalUserQueries
import ai.wandering.retriever.common.storekit.entities.UserAction
import ai.wandering.retriever.common.storekit.entities.note.Channel
import ai.wandering.retriever.common.storekit.entities.note.Mention
import ai.wandering.retriever.common.storekit.entities.`Note.Output.Populated`
import ai.wandering.retriever.common.storekit.entities.note.Tag
import ai.wandering.retriever.common.storekit.entities.user.output.Graph
import ai.wandering.retriever.common.storekit.entities.user.output.User
import kotlinx.datetime.Instant

fun LocalTagQueries.getAllAsList() = getAll().executeAsList()
fun LocalNoteQueries.findById(id: String) = getById(id).executeAsOne()
fun LocalUserQueries.getAllNotes(id: String) = getNotesByUserId(id).executeAsOne()
fun LocalMentionQueries.findAndPopulateOtherUsers(userId: String): List<User> {
    val response = findAndPopulateOtherUsersByUserId(userId).executeAsList()
    return response
        .filter { row -> row.id != null && row.username != null && row.name != null && row.email != null }
        .map {
            User(
                id = it.id!!,
                username = it.username!!,
                name = it.name!!,
                email = it.email!!,
                avatarUrl = it.avatarUrl,
            )
        }
}

fun LocalUserQueries.findAndPopulateByUserId(userId: String): User {
    val userResponse = findAndPopulate(userId).executeAsList()

    val common = userResponse.first()

    val followed = userResponse
        .filter { row -> row.followedUserId != null }
        .map { row ->
            User(
                id = row.followedUserId!!,
                username = row.followedUsername!!,
                name = row.followedName!!,
                email = row.followedEmail!!,
                avatarUrl = row.followedAvatarUrl,
                bio = row.followedBio,
            )
        }
        .distinct()

    val userActionsResponse = getUserActionsByUserId(listOf(userId)).executeAsList()

    val userActions = userActionsResponse.map { row -> UserAction(row.userId, row.objectId, row.type) }

    val followers = userResponse
        .filter { row -> row.followerId != null }
        .map { row ->
            User(
                id = row.followerId!!,
                username = row.followerUsername!!,
                name = row.followerName!!,
                email = row.followerEmail!!,
                avatarUrl = row.followerAvatarUrl,
                bio = row.followerBio,
            )
        }
        .distinct()

    val graphs = userResponse.filter { row -> row.graphId != null }.map { row -> Graph(row.graphId!!, row.graphName!!, row.graphOwnerId!!) }.distinct()
    val followedTags = userResponse.filter { row -> row.followedTagId != null }.map { row -> Tag(row.followedTagId!!, row.followedTagName!!) }.distinct()
    val followedGraphs =
        userResponse.filter { row -> row.followedGraphId != null }.map { row -> Graph(row.followedGraphId!!, row.followedGraphName!!, row.followedGraphOwnerId!!) }.distinct()
    val pinnedGraphs =
        userResponse.filter { row -> row.pinnedGraphId != null }.map { row -> Graph(row.pinnedGraphId!!, row.pinnedGraphName!!, row.pinnedGraphOwnerId!!) }.distinct()
    val pinnedChannels = userResponse.filter { row -> row.pinnedChannelId != null }
        .map { row ->
            Channel(
                row.pinnedChannelId!!,
                row.id,
                row.pinnedChannelGraphId!!,
                Tag(row.pinnedChannelTagId!!, row.pinnedChannelTagName!!),
                Instant.parse(row.pinnedChannelCreated!!)
            )
        }.distinct()

    return User(
        id = common.id,
        username = common.username,
        name = common.name,
        email = common.email,
        avatarUrl = common.avatarUrl,
        bio = common.bio,
        followed = followed,
        followers = followers,
        graphs = graphs,
        tagsFollowing = followedTags,
        graphsFollowing = followedGraphs,
        pinnedGraphs = pinnedGraphs,
        pinnedChannels = pinnedChannels,
        coverImageUrl = common.coverImageUrl,
        actions = userActions
    )
}

fun LocalNoteQueries.findAndPopulate(id: String): `Note.Output.Populated` {
    val response = getByIdAndPopulateAll(id).executeAsList()

    val common = response.first()

    val user = User(
        id = common.userId!!,
        username = common.userUsername!!,
        name = common.userName!!,
        email = common.userEmail!!,
        avatarUrl = common.userAvatarUrl
    )

    val tagIdToTag = mutableMapOf<String, Tag>()

    response
        .filter { row -> row.tagId != null && row.tagName != null }
        .map { row -> Tag(row.tagId!!, row.tagName!!) }
        .distinct()
        .onEach { tagIdToTag[it.id] = it }

    val channels = response
        .filter { row -> row.channelId != null && row.channelTagId != null && row.channelGraphId != null && row.channelUserId != null }
        .map { row -> Channel(row.channelId!!, row.channelUserId!!, row.channelGraphId!!, tagIdToTag[row.channelTagId!!]!!, Instant.parse(row.channelCreated!!)) }

    val mentions = response
        .filter { row -> row.userId != null && row.otherUserId != null && row.otherUserName != null && row.otherUserEmail != null && row.otherUserAvatarUrl != null }
        .map { row -> Mention(row.userId!!, row.otherUserId!!, User(row.otherUserId, row.otherUserUsername!!, row.otherUserName!!, row.otherUserEmail!!, row.userAvatarUrl)) }
        .distinct()

    return `Note.Output.Populated`(
        id = common.id,
        user = user,
        content = common.content ?: "",
        isRead = common.is_read,
        channels = channels,
        mentions = mentions,
        references = listOf(),
        created = Instant.parse(common.created)
    )
}

