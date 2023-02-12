package ai.wandering.retriever.android.common.sig.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

@ReadOnlyComposable
@Composable
fun systemThemeColors() = if (isSystemInDarkTheme()) SigColors.Dark.create() else SigColors.Light.create()

@ReadOnlyComposable
@Composable
fun systemColorScheme() = systemThemeColors().asColorScheme()