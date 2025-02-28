package com.amirnlz.tasko.core.ui.components.calendar

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

data class CalendarDay(
    val date: LocalDate,
    val isToday: Boolean,
    val isPast: Boolean,
    val dayOfMonth: Int,
    val dayOfWeek: String
)

object CalendarDates {
    fun generateMonthDates(
        year: Int = LocalDate.now().year,
        month: Int = LocalDate.now().monthValue,
        dateFormatter: (LocalDate) -> String = { it.formatDayOfWeek() }
    ): List<CalendarDay> {
        val currentDate = LocalDate.now()
        val yearMonth = YearMonth.of(year, month)

        return (1..yearMonth.lengthOfMonth()).map { day ->
            val date = yearMonth.atDay(day)
            CalendarDay(
                date = date,
                isToday = date == currentDate,
                isPast = date.isBefore(currentDate),
                dayOfMonth = day,
                dayOfWeek = dateFormatter(date)
            )
        }
    }

    private fun LocalDate.formatDayOfWeek(): String {
        return format(DateTimeFormatter.ofPattern("EEE"))
    }
}