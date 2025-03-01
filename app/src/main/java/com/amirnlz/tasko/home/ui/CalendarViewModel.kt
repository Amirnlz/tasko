package com.amirnlz.tasko.home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import com.amirnlz.tasko.core.ui.components.calendar.CalendarState
import java.time.LocalDate
import java.time.YearMonth

sealed interface CalendarEvent {
    data class DateSelected(val date: LocalDate) : CalendarEvent
    data class YearMonthChanged(val yearMonth: YearMonth) : CalendarEvent
}

class CalendarViewModel : ViewModel() {
    private val _state = mutableStateOf(CalendarState())
    val state: State<CalendarState> = _state


    init {
        loadMonth(YearMonth.now())
    }

    fun onEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.DateSelected -> {
                _state.value = _state.value.copy(selectedDate = event.date)
            }

            is CalendarEvent.YearMonthChanged -> {
                loadMonth(event.yearMonth)
            }
        }

    }

    private fun loadMonth(yearMonth: YearMonth) {
        _state.value = _state.value.copy(
            displayedYearMonth = yearMonth,
//            days = CalendarDates.generateMonthDates(yearMonth.year, yearMonth.monthValue)
        )
    }
}