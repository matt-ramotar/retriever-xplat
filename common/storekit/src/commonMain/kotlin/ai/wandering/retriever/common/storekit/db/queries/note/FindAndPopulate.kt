package ai.wandering.retriever.common.storekit.db.queries.note

import ai.wandering.retriever.common.storekit.LocalNoteQueries
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.Mention
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.entity.User
import kotlinx.datetime.Instant

fun LocalNoteQueries.findAndPopulate(id: String): Note.Output.Populated {
    val response = findByIdAndPopulateAll(id).executeAsList()

    val common = response.first()

    val user = User.Output.Node(
        id = common.userId!!,
        username = common.userUsername!!,
        name = common.userName!!,
        email = common.userEmail!!,
        avatarUrl = common.userAvatarUrl,
        coverImageUrl = common.userCoverImageUrl,
        bio = common.userBio
    )

    val channels = response
        .filter { row -> row.channelId != null && row.channelTagId != null && row.channelGraphId != null && row.channelUserId != null }
        .map { row -> Channel.Output.Node(row.channelId!!, Instant.parse(row.channelCreatedAt!!)) }

    val mentions = response
        .filter { row -> row.userId != null && row.otherUserId != null && row.otherUserName != null && row.otherUserEmail != null && row.otherUserAvatarUrl != null }
        .map { row -> Mention.Output.Node(row.id, row.userId!!, row.otherUserId!!) }
        .distinct()

    return Note.Output.Populated(
        id = common.id,
        user = user,
        content = common.content ?: "",
        isRead = common.is_read,
        channels = channels,
        mentions = mentions,
        createdAt = Instant.parse(common.createdAt),
        updatedAt = Instant.parse(common.updatedAt),
        noteRelationships = listOf(),
        threadNotes = listOf(),
        pinners = listOf()
    )
}
