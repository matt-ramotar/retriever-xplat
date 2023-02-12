package ai.wandering.retriever.android.app.auth.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import ai.wandering.retriever.android.common.sig.color.SigColors

@Composable
fun DemoSignInButton(onClick: () -> Unit) {
    val colors = if (isSystemInDarkTheme()) SigColors.Dark.create() else SigColors.Light.create()

    SignInButton(
        colors = ButtonDefaults.elevatedButtonColors(containerColor = colors.rose.background),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
        ) {

            Text(text = "Demo", style = TextStyle(color = colors.rose.text, fontWeight = FontWeight.Bold))
        }
    }
}