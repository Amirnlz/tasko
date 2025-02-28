package com.amirnlz.tasko.core.ui.components.calendar

import java.time.LocalDate

object CalendarConstants {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun getYearsRange(): List<Int> {
        val currentYear = LocalDate.now().year
        return (currentYear - 100..currentYear + 10).toList()
    }
}
