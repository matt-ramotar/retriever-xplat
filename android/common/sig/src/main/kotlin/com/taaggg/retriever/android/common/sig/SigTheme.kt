package com.taaggg.retriever.android.common.sig

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.taaggg.retriever.android.common.sig.color.LocalColorScheme
import com.taaggg.retriever.android.common.sig.color.Sig

@Composable
fun SigTheme(
    colorScheme: ColorScheme = Sig.ColorScheme,
    shapes: Shapes = Shapes(),
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
    ) {
        MaterialTheme(colorScheme = colorScheme, shapes = shapes, content = content)
    }
}