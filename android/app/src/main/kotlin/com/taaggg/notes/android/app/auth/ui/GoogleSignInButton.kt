package com.taaggg.notes.android.app.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.unit.sp
import com.taaggg.notes.android.app.R

@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    ElevatedButton(
        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.White),
        onClick = onClick,
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(0.dp),
        elevation = ButtonDefaults.buttonElevation(32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google), contentDescription = null, tint = Color.Unspecified, modifier = Modifier
                    .size(32.dp)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = "Continue with Google", style = TextStyle(color = Color(135, 135, 135), fontWeight = FontWeight(400), fontSize = 16.sp))
        }
    }
}