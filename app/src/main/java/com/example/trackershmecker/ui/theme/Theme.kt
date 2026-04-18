package com.example.trackershmecker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    background = Background,
    surface = Background,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
)

@Composable
fun TrackerShmeckerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content,
    )
}
