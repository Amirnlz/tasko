package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.YearMonth

@Immutable
data class CalendarState(
    val selectedDate: LocalDate? = null,
    val currentDate: LocalDate = LocalDate.now(),
    val displayedYearMonth: YearMonth = YearMonth.now()
)