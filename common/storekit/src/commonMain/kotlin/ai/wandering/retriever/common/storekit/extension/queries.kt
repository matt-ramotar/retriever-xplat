package ai.wandering.retriever.common.storekit.extension

import ai.wandering.retriever.common.storekit.LocalMentionQueries
import ai.wandering.retriever.common.storekit.LocalNoteQueries
import ai.wandering.retriever.common.storekit.LocalTagQueries
import ai.wandering.retriever.common.storekit.LocalUserQueries
import ai.wandering.retriever.common.storekit.entities.note.Channel
import ai.wandering.retriever.common.storekit.entities.note.Mention
import ai.wandering.retriever.common.storekit.entities.note.Note
import ai.wandering.retriever.common.storekit.entities.note.Tag
import ai.wandering.retriever.common.storekit.entities.user.output.User

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
                avatarUrl = it.avatarUrl

            )
        }
}

fun LocalNoteQueries.findAndPopulate(id: String): Note {
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
        .map { row -> Channel(row.channelId!!, row.channelUserId!!, row.channelGraphId!!, tagIdToTag[row.channelTagId!!]!!) }

    val mentions = response
        .filter { row -> row.userId != null && row.otherUserId != null && row.otherUserName != null && row.otherUserEmail != null && row.otherUserAvatarUrl != null }
        .map { row -> Mention(row.userId!!, row.otherUserId!!, User(row.otherUserId, row.otherUserUsername!!, row.otherUserName!!, row.otherUserEmail!!, row.userAvatarUrl)) }
        .distinct()

    return Note(
        id = common.id,
        user = user,
        content = common.content ?: "",
        isRead = common.is_read,
        channels = channels,
        mentions = mentions,
        parents = listOf(),
        references = listOf(),
        children = listOf()
    )
}

