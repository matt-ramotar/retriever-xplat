@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.feature.search_tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ai.wandering.retriever.android.common.sig.SigTheme
import ai.wandering.retriever.android.common.sig.color.systemThemeColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment

@Composable
fun SearchTab() {
    val colors = systemThemeColors()

    val searchState = remember { mutableStateOf(TextFieldValue()) }

    SigTheme {
        
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            
            Text(text = "", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onBackground)
            
        }

        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextField(
                    value = searchState.value,
                    onValueChange = { searchState.value = it },
                    placeholder = { Text(text = "Enter a search term") },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
                    },
                    colors = TextFieldDefaults.textFieldColors(unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            LazyColumn {

                item {
                    ListItem(
                        text = "Browse People",
                        leadingIcon = painterResource(id = R.drawable.person_multiple),
                        trailingIcon = painterResource(id = R.drawable.chevron_right)
                    ) {
                    }
                }

                item {
                    ListItem(text = "Browse Channels", leadingIcon = painterResource(id = R.drawable.hashtag), trailingIcon = painterResource(id = R.drawable.chevron_right)) {
                    }
                }

                item {
                    Text(text = "Recent Searches")
                }

                item {
                    ListItem(text = "in:#skiing", leadingIcon = painterResource(id = R.drawable.clock), trailingIcon = painterResource(id = R.drawable.close)) {
                    }
                }

                item {
                    ListItem(text = "in:#black-crows", leadingIcon = painterResource(id = R.drawable.clock), trailingIcon = painterResource(id = R.drawable.close)) {
                    }
                }

                item {
                    Text(text = "Narrow Your Search")
                }

                item {
                    SearchFilter(text = "author:", example = "Ex. @matt") {

                    }
                }

                item {
                    SearchFilter(text = "is:", example = "Ex. saved") {

                    }
                }

                item {
                    SearchFilter(text = "after:", example = "Ex. 2023-02-01") {

                    }
                }

                item {
                    SearchFilter(text = "in:", example = "Ex. #black-crows") {

                    }
                }
            }
        }
    }
}


@Composable
private fun ListItem(
    text: String,
    leadingIcon: Painter,
    trailingIcon: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(painter = leadingIcon, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(Modifier.size(8.dp))
            Text(text = text)
        }
        Icon(painter = trailingIcon, contentDescription = null, modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun SearchFilter(
    text: String,
    example: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(painterResource(id = R.drawable.add_circle), contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(Modifier.size(8.dp))
            Text(text = text)
        }
        Text(text = example)
    }
}
