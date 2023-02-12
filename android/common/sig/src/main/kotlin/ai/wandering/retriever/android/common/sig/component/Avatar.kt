package ai.wandering.retriever.android.common.sig.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Avatar(avatarUrl: String, size: Dp = 96.dp) {
    AsyncImage(
        model = avatarUrl, contentDescription = "", modifier = Modifier
            .clip(CircleShape)
            .size(size), contentScale = ContentScale.Crop
    )
}