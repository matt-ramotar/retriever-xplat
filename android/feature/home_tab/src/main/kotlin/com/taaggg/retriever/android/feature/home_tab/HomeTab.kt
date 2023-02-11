package com.taaggg.retriever.android.feature.home_tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaggg.retriever.common.storekit.db.Collection
import com.taaggg.retriever.common.storekit.entities.note.Mention
import com.taaggg.retriever.common.storekit.entities.note.Tag

@Composable
fun HomeTab(tags: Collection<Tag>, mentions: Collection<Mention>, onNavigateToNotesTab: (name: String) -> Unit) {

    LazyColumn {
        item {
            Text(text = "Notes")
        }

        item {
            Text(text = "Trash")
        }

        item {
            Spacer(modifier = Modifier.size(32.dp))
        }

        items(mentions.find()) { mention ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                onNavigateToNotesTab(mention.name)
            }) {
                Text(text = "@", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.size(16.dp))
                Text(text = mention.name, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.size(12.dp))
        }

        items(tags.find()) { tag ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                onNavigateToNotesTab(tag.name)
            }) {
                Text(text = "#", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.size(16.dp))
                Text(text = tag.name, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.size(12.dp))
        }

    }
}


