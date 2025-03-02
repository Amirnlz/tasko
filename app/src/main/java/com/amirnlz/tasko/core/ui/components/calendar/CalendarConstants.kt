package com.amirnlz.tasko.core.ui.components.calendar

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarConstants {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun getYearsRange(selectedYear: Int? = null): List<Int> {
        val currentYear = LocalDate.now().year
        val minYear = selectedYear?.let { minOf(it, currentYear - 5) } ?: (currentYear - 5)
        val maxYear = selectedYear?.let { maxOf(it, currentYear + 10) } ?: (currentYear + 10)
        return (minYear..maxYear).toList()
    }

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
