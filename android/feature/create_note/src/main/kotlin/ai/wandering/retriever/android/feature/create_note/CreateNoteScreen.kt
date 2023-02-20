@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.feature.create_note

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CreateNoteScreen() {

    val text = remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = text.value,
        onValueChange = { text.value = it },
    )
}