package com.amirnlz.tasko.home.ui

import androidx.lifecycle.ViewModel
import com.amirnlz.tasko.core.ui.components.calendar.CalendarConstants
import com.amirnlz.tasko.core.ui.components.calendar.CalendarState
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth

sealed interface CalendarEvent {
    data class DayChanged(val date: LocalDate) : CalendarEvent
    data class YearMonthChanged(val yearMonth: YearMonth) : CalendarEvent
}

class CalendarViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state.asStateFlow()

    init {
        updateMonth(YearMonth.now())
    }

    fun onEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.DayChanged -> handleDayChanged(event.date)
            is CalendarEvent.YearMonthChanged -> handleYearMonthChanged(event.yearMonth)
        }
    }

    private fun handleDayChanged(date: LocalDate) {
        _state.update { it.copy(selectedDate = date) }
    }

    private fun handleYearMonthChanged(yearMonth: YearMonth) {
        updateMonth(yearMonth)
    }

    private fun updateMonth(yearMonth: YearMonth) {
        _state.update { currentState ->
            val newDays = CalendarConstants.generateMonthDates(yearMonth.year, yearMonth.monthValue)
            val newSelectedDate =
                if (LocalDate.now().yearMonth == yearMonth) LocalDate.now() else yearMonth.atDay(1)

            currentState.copy(
                days = newDays,
                selectedDate = newSelectedDate,
                selectedYearMonth = yearMonth
            )
        }
    }
}