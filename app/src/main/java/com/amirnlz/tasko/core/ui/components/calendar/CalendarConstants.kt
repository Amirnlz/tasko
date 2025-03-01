package com.amirnlz.tasko.core.ui.components.calendar

import java.time.LocalDate

object CalendarConstants {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun getYearsRange(selectedYear: Int? = null): List<Int> {
        val currentYear = LocalDate.now().year
        val minYear = selectedYear?.let { minOf(it, currentYear - 100) } ?: (currentYear - 100)
        val maxYear = selectedYear?.let { maxOf(it, currentYear + 10) } ?: (currentYear + 10)
        return (minYear..maxYear).toList()
    }
}
