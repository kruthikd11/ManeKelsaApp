package com.example.manekelsaapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun ManeKelsaAppTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme() // You can customize colors here
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}