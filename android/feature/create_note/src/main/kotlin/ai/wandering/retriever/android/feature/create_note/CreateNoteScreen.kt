@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.feature.create_note

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateNoteScreen(noteCreationViewModel: NoteCreationViewModel = viewModel()) {

    val state = noteCreationViewModel.state.collectAsState()


    TextField(
        value = state.value.note.content,
        onValueChange = { noteCreationViewModel.updateContent(it) },
    )
}