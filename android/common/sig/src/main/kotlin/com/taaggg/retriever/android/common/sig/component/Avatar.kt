package com.taaggg.retriever.android.common.sig.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Avatar(avatarUrl: String) {
    AsyncImage(
        model = avatarUrl, contentDescription = "", modifier = Modifier
            .clip(CircleShape)
            .size(96.dp)
    )
}