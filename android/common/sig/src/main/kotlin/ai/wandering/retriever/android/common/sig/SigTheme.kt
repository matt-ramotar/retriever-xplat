package ai.wandering.retriever.android.common.sig

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import ai.wandering.retriever.android.common.sig.color.LocalColorScheme
import ai.wandering.retriever.android.common.sig.color.Sig

@Composable
fun SigTheme(
    colorScheme: ColorScheme = Sig.ColorScheme,
    shapes: Shapes = Shapes(
        extraSmall = RoundedCornerShape(0.dp),
        small = RoundedCornerShape(0.dp),
        medium = RoundedCornerShape(0.dp),
        large = RoundedCornerShape(0.dp),
        extraLarge = RoundedCornerShape(0.dp),
    ),

    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
    ) {
        MaterialTheme(colorScheme = colorScheme, shapes = shapes, content = content)
    }
}