@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.feature.create_note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.Material3RichText

@Composable
fun CreateNoteScreen(noteCreationViewModel: NoteCreationViewModel = viewModel()) {

    val state = noteCreationViewModel.state.collectAsState()

    Material3RichText {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = state.value.note.content,
                onValueChange = {
                    noteCreationViewModel.updateContent(it)
                    println(it.trimIndent())
                },
            )

            Markdown(content = state.value.note.content)

        }

    }


}