package ai.wandering.retriever.android.common.sig.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ai.wandering.retriever.android.common.sig.color.systemThemeColors
import androidx.compose.material3.Button as Material3Button

@Composable
fun PrimaryButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    val colors = systemThemeColors()
    Material3Button(
        onClick = onClick,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colors.accent)
    ) {
        content()
    }
}

@Composable
fun OpacityButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    val colors = systemThemeColors()
    Material3Button(
        onClick = onClick,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colors.opacity1, contentColor = colors.standard.text)
    ) {
        content()
    }
}