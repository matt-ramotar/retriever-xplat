package ai.wandering.retriever.common.storekit.extension

import ai.wandering.retriever.common.storekit.LocalNoteQueries
import ai.wandering.retriever.common.storekit.LocalTagQueries
import ai.wandering.retriever.common.storekit.LocalUserQueries
import ai.wandering.retriever.common.storekit.entities.note.Mention
import ai.wandering.retriever.common.storekit.entities.note.Note
import ai.wandering.retriever.common.storekit.entities.note.Tag
import ai.wandering.retriever.common.storekit.entities.user.output.User

fun LocalTagQueries.getAllAsList() = getAll().executeAsList()
fun LocalNoteQueries.findById(id: String) = getById(id).executeAsOne()
fun LocalUserQueries.getAllNotes(id: String) = getNotesByUserId(id).executeAsOne()


fun LocalNoteQueries.findAndPopulate(id: String): Note {
    val response = getByIdAndPopulateAll(id).executeAsList()

    val common = response.first()

    val user = User(
        id = common.userId!!,
        name = common.userName!!,
        email = common.userEmail!!,
        avatarUrl = common.userAvatarUrl
    )
    val tags = response
        .filter { row -> row.tagId != null && row.tagName != null }
        .map { row -> Tag(row.tagId!!, row.tagName!!) }
        .distinct()

    val mentions = response
        .filter { row -> row.userId != null && row.otherUserId != null && row.otherUserName != null && row.otherUserEmail != null && row.otherUserAvatarUrl != null }
        .map { row -> Mention(row.userId!!, row.otherUserId!!, User(row.otherUserId!!, row.otherUserName!!, row.otherUserEmail!!, row.userAvatarUrl)) }
        .distinct()

    return Note(
        id = common.id,
        user = user,
        content = common.content ?: "",
        isRead = common.is_read,
        tags = tags,
        mentions = mentions,
        parents = listOf(),
        references = listOf(),
        children = listOf()
    )
}

