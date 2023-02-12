package ai.wandering.retriever.android.app.auth.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ai.wandering.retriever.android.app.R
import ai.wandering.retriever.android.common.sig.color.SigColors

@Composable
fun GoogleSignInButton(onClick: () -> Unit) {

    val colors = if (isSystemInDarkTheme()) SigColors.Dark.create() else SigColors.Light.create()

    SignInButton(
        colors = ButtonDefaults.elevatedButtonColors(containerColor = colors.blue.background),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
        ) {
            Icon(painter = painterResource(id = R.drawable.google), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(28.dp))

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = "Continue with Google", style = TextStyle(color = colors.blue.text, fontWeight = FontWeight.Bold))
        }
    }
}