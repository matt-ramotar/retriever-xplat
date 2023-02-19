package ai.wandering.retriever.android.feature.finder_tab

import ai.wandering.retriever.android.common.sig.component.Avatar
import ai.wandering.retriever.common.storekit.entity.User
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(user: User.Output.Populated) {

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                user.coverImageUrl, null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 40.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom
            ) {
                val avatarUrl = user.avatarUrl
                if (avatarUrl != null) {
                    Avatar(avatarUrl = avatarUrl, size = 120.dp)
                }
            }
        }

        Text(text = user.name, style = MaterialTheme.typography.titleLarge)


        if (user.bio != null) {
            Text(user.bio!!, style = MaterialTheme.typography.bodyMedium)
        }

        user.pinnedChannels.forEach {
            Text("Pinned Channel: ${it.id}")
        }

        user.followedUsers.forEach {
            Text("Following: ${it.name}")
        }
        if (user.userActions.isNotEmpty()) {
            Text(text = "Activity")
            user.userActions.forEach { action -> Text(text = action.type.name) }
        }


    }
}