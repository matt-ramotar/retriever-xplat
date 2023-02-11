package com.taaggg.retriever.android.common.sig.color

import androidx.compose.material3.ColorScheme

internal fun Colors.asColorScheme(): ColorScheme = ColorScheme(
    primary = accent,
    onPrimary = if (isLight) secondary else standard.text,
    primaryContainer = standard.background,
    onPrimaryContainer = standard.text,
    inversePrimary = standard.background,
    secondary = accent,
    onSecondary = if (isLight) secondary else standard.text,
    secondaryContainer = standard.background,
    onSecondaryContainer = standard.text,
    tertiary = accent,
    onTertiary = if (isLight) secondary else standard.text,
    tertiaryContainer = standard.background,
    onTertiaryContainer = standard.text,
    background = standard.background,
    onBackground = standard.text,
    surface = secondary,
    onSurface = standard.text,
    surfaceVariant = secondary,
    surfaceTint = secondary,
    inverseSurface = standard.background,
    inverseOnSurface = standard.text,
    error = alert.text,
    errorContainer = alert.background,
    onError = standard.text,
    onErrorContainer = standard.text,
    outline = standard.border,
    outlineVariant = standard.border,
    scrim = secondary,
    onSurfaceVariant = standard.text
)