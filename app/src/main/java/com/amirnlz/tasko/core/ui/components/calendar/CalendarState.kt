package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class CalendarState(
    val days: List<CalendarDay>,
    val selectedDate: LocalDate? = null,
    val currentDate: LocalDate = LocalDate.now()
)

sealed interface CalendarEvent {
    data class DateSelected(val date: LocalDate) : CalendarEvent
}