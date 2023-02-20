package ai.wandering.retriever.android.feature.create_note

import ai.wandering.retriever.common.storekit.converter.asNodeOutput
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.Mention
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.entity.NoteRelationship
import ai.wandering.retriever.common.storekit.entity.ThreadNote
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.helper.ObjectId
import ai.wandering.retriever.common.storekit.repository.NoteRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class NoteCreationViewModel(user: AuthenticatedUser, private val noteRepository: NoteRepository) : ViewModel() {


    private val stateFlow = MutableStateFlow(NoteCreationState(MutableNote.Draft(user = user.asNodeOutput())))
    val state: StateFlow<NoteCreationState> = stateFlow
    private fun setState(state: NoteCreationState) {
        stateFlow.value = state
    }

    fun updateContent(nextContent: String) {

        val nextNote = when (val note = stateFlow.value.note) {
            is MutableNote.Created -> note.copy(content = nextContent)
            is MutableNote.Draft -> note.copy(content = nextContent)
        }

        val nextState = stateFlow.value.copy(note = nextNote)

        setState(nextState)
    }

}


data class NoteCreationState(
    val note: MutableNote
) {
    private val _lastWriteNetwork = MutableStateFlow<Instant?>(null)
    private val _lastWriteLocal = MutableStateFlow<Instant?>(null)
    private val _syncing = MutableStateFlow(false)

    val lastWriteNetwork: StateFlow<Instant?> = _lastWriteNetwork
    val lastWriteLocal: StateFlow<Instant?> = _lastWriteLocal
    val syncing: StateFlow<Boolean> = _syncing

    fun isSynced() = lastWriteLocal.value == lastWriteNetwork.value

    fun setLastWriteNetwork(value: Instant) {
        _lastWriteNetwork.value = value
    }

    fun setLastWriteLocal(value: Instant) {
        _lastWriteLocal.value = value
    }

    fun setSyncing(value: Boolean) {
        _syncing.value = value
    }
}

sealed class MutableNote {

    abstract val id: String
    abstract val user: User.Output.Node
    abstract var content: String
    abstract val channels: MutableList<Channel.Output.Node>
    abstract val mentions: MutableList<Mention.Output.Node>
    abstract val noteRelationships: MutableList<NoteRelationship.Output.Node>

    data class Created(
        override val id: String,
        override val user: User.Output.Node,
        override var content: String,
        override val channels: MutableList<Channel.Output.Node>,
        override val mentions: MutableList<Mention.Output.Node>,
        override val noteRelationships: MutableList<NoteRelationship.Output.Node>,
        val createdAt: Instant,
        val updatedAt: Instant,
        val threadNotes: MutableList<ThreadNote.Output.Node>,
        val pinners: MutableList<User.Output.Node>,
        val isRead: Boolean,
    ) : MutableNote()

    data class Draft(
        override val id: String = ObjectId(),
        override val user: User.Output.Node,
        override var content: String = "",
        override val channels: MutableList<Channel.Output.Node> = mutableListOf(),
        override val mentions: MutableList<Mention.Output.Node> = mutableListOf(),
        override val noteRelationships: MutableList<NoteRelationship.Output.Node> = mutableListOf()
    ) : MutableNote()

}


fun MutableNote.Draft.asPopulatedDraftOutput(): Note.Output.Populated.Draft {
    val createdAt = Clock.System.now()
    return Note.Output.Populated.Draft(
        id = id,
        user = user,
        content = content,
        isRead = false,
        createdAt = createdAt,
        updatedAt = createdAt,
        channels = channels,
        mentions = mentions,
        noteRelationships = noteRelationships,
        threadNotes = listOf(),
        pinners = listOf()
    )
}


fun MutableNote.Created.asPopulatedCreatedOutput() = Note.Output.Populated.Created(
    id = id,
    user = user,
    content = content,
    isRead = isRead,
    createdAt = createdAt,
    updatedAt = updatedAt,
    channels = channels,
    mentions = mentions,
    noteRelationships = noteRelationships,
    threadNotes = threadNotes,
    pinners = pinners,
)

