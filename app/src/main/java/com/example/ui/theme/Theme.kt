package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NumismaColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF1F1604),
    primaryContainer = Color(0xFF382A09),
    onPrimaryContainer = GoldLight,
    secondary = GoldSecondary,
    onSecondary = Color(0xFF1E1605),
    secondaryContainer = CardSurfaceElevated,
    onSecondaryContainer = TextPrimary,
    tertiary = BronzeAccent,
    onTertiary = Color.White,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = MidnightSurface,
    onSurface = TextPrimary,
    surfaceVariant = CardSurface,
    onSurfaceVariant = TextSecondary,
    outline = GoldBorder,
    outlineVariant = Color(0xFF26324B),
    error = DangerRed,
    onError = Color.White
)

@Composable
fun NumismaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NumismaColorScheme,
        typography = Typography,
        content = content
    )
}
