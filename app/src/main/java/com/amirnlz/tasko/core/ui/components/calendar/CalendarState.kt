package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.YearMonth

@Immutable
data class CalendarState(
    val days: List<CalendarDay> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedYearMonth: YearMonth = YearMonth.now()
)


@Immutable
data class CalendarDay(
    val date: LocalDate,
    val isToday: Boolean,
    val isPast: Boolean,
    val dayOfMonth: Int,
    val dayOfWeek: String
)