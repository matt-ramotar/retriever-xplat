package ai.wandering.retriever.android.app.auth.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun SignInButton(
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    shape: Shape = RoundedCornerShape(0.dp),
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(8.dp),
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    ElevatedButton(
        colors = colors,
        onClick = onClick,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        modifier = Modifier.fillMaxWidth()
    ) {
        content()
    }
}