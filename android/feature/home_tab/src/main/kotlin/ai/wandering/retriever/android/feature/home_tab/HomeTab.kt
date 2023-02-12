@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.feature.home_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ai.wandering.retriever.android.common.sig.color.Sig
import ai.wandering.retriever.android.common.sig.component.Avatar
import ai.wandering.retriever.common.storekit.LocalMentionQueries
import ai.wandering.retriever.common.storekit.LocalTagQueries
import ai.wandering.retriever.common.storekit.entities.user.output.User

@Composable
fun HomeTab(
    user: User,
    tags: LocalTagQueries,
    mentions: LocalMentionQueries,
    onNavigateToMentionResults: (otherUserId: String) -> Unit,
    onNavigateToTagResults: (name: String) -> Unit
) {

    val searchState = remember { mutableStateOf(TextFieldValue()) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                if (user.avatarUrl != null) {
                    Avatar(avatarUrl = user.avatarUrl!!, size = 32.dp)
                }

                Spacer(modifier = Modifier.size(12.dp))
                Text(text = user.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            }

            Icon(painter = painterResource(id = R.drawable.sort_type), contentDescription = null, modifier = Modifier.size(32.dp))
        }

        LazyColumn {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextField(
                        value = searchState.value,
                        onValueChange = { searchState.value = it },
                        placeholder = { Text(text = "Jump to...") },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
                        },
                        colors = TextFieldDefaults.textFieldColors(unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            item {
                Text(text = "Notes")
            }

            item {
                Text(text = "Trash")
            }

            item {
                Spacer(modifier = Modifier.size(32.dp))
            }

            items(mentions.getAll().executeAsList()) { mention ->
                MentionEntryPoint(name = mention.otherUserId, unreadMentions = 2, unreadMessages = 4, onNavigateToNotesTab = onNavigateToMentionResults)
                Spacer(modifier = Modifier.size(12.dp))
            }

            items(tags.getAll().executeAsList()) { tag ->
                ChannelEntryPoint(name = tag.name, unreadMentions = 2, unreadMessages = 4, onNavigateToNotesTab = onNavigateToTagResults)
                Spacer(modifier = Modifier.size(12.dp))
            }

        }
    }
}


@Composable
private fun ChannelEntryPoint(name: String, unreadMentions: Int, unreadMessages: Int, onNavigateToNotesTab: (tag: String) -> Unit) {
    val painter = painterResource(id = R.drawable.hashtag)
    EntryPoint(name, painter, unreadMentions, unreadMessages, onNavigateToNotesTab)
}

@Composable
private fun MentionEntryPoint(name: String, unreadMentions: Int, unreadMessages: Int, onNavigateToNotesTab: (tag: String) -> Unit) {
    val painter = painterResource(id = R.drawable.mention)
    EntryPoint(name, painter, unreadMentions, unreadMessages, onNavigateToNotesTab)
}


@Composable
private fun EntryPoint(name: String, leadingIcon: Painter, unreadMentions: Int, unreadMessages: Int, onNavigateToNotesTab: (tag: String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onNavigateToNotesTab(name)
        }) {
        Row {
            Icon(painter = leadingIcon, contentDescription = null)
            Spacer(Modifier.size(16.dp))
            Text(text = name, style = MaterialTheme.typography.titleMedium)
        }

        Row() {
            Chip(onClick = { /*TODO*/ }, backgroundColor = Sig.ColorScheme.error, shape = CircleShape) {
                Text(text = "$unreadMentions", color = Sig.ColorScheme.onBackground, fontWeight = FontWeight.Bold)
            }
        }

    }

}


@Composable
fun Chip(onClick: () -> Unit, backgroundColor: Color, shape: Shape = RoundedCornerShape(8.dp), label: @Composable () -> Unit) {
    Row(modifier = Modifier
        .clip(shape)
        .background(backgroundColor)
        .padding(4.dp)
        .clickable { onClick() }) {
        label()
    }
}