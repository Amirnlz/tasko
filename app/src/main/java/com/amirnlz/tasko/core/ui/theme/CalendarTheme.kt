package com.amirnlz.tasko.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


object CalendarTheme {
    @Composable
    fun pastDateColor() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    @Composable
    fun futureDateColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun todayBackground() = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    @Composable
    fun todayTextColor() = MaterialTheme.colorScheme.primary
}