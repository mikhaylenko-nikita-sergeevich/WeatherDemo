package com.mikhaylenko.weatherdemo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = DarkText, // Primary text color
    onPrimary = LightBackground, // Color for content on primary
    secondary = LightText, // Secondary text color
    onSecondary = LightBackground, // Color for content on secondary
    background = LightBackground, // Overall background
    onBackground = DarkText, // Text on background
    surface = CardBackground, // Card background
    onSurface = DarkText // Text on card background
)

@Composable
fun WeatherDemoTheme(
    // Force light theme as per reference image
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme, // Always use light scheme for this design
        typography = Typography, // Will adjust Typography in Type.kt
        content = content
    )
}