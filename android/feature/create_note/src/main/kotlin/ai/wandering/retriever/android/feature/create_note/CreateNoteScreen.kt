@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.feature.create_note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.Material3RichText

@Composable
fun CreateNoteScreen(noteCreationViewModel: NoteCreationViewModel = viewModel()) {

    val state = noteCreationViewModel.state.collectAsState()
    val isSyncing = state.value.syncing.collectAsState()
    val updatedNetworkAt = state.value.lastWriteNetwork.collectAsState()
    val isSynced = state.value.isSynced.collectAsState()

    Material3RichText {
        Column(modifier = Modifier.fillMaxSize()) {
            if (isSyncing.value) {
                Text(text = "Syncing")
            }

            if (isSynced.value) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.Green)
            }

            Text(text = "Last synced: ${updatedNetworkAt.value.toString()}")

            TextField(
                value = state.value.note.content,
                onValueChange = {
                    noteCreationViewModel.updateContent(it)
                },
            )

            Button(onClick = { noteCreationViewModel.create() }) {
                Text(text = "Create")
            }

            Markdown(content = state.value.note.content)

        }

    }


}