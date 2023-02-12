package ai.wandering.retriever.android.common.sig.color

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable


object Sig {
    private val Colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val ColorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = Colors.asColorScheme()
}