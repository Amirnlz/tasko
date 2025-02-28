package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarMonthYearPicker(
    state: CalendarState,
    modifier: Modifier = Modifier,
    onEvent: (CalendarEvent) -> Unit = {}
) {
    var showMonthYearPicker by remember { mutableStateOf(false) }

    // Bottom sheet state
    val sheetState = rememberModalBottomSheetState()

    Box(modifier = modifier) {
        Column {
            CalendarHeader(
                yearMonth = state.displayedMonth,
                onPreviousClick = {
                    val previousMonth = state.displayedMonth.minusMonths(1)
                    onEvent(CalendarEvent.MonthChanged(previousMonth))
                },
                onNextClick = {
                    val nextMonth = state.displayedMonth.plusMonths(1)
                    onEvent(CalendarEvent.MonthChanged(nextMonth))
                },
                onHeaderClick = { showMonthYearPicker = true }
            )

            // Rest of the calendar implementation...
        }

        if (showMonthYearPicker) {
            MonthYearPicker(
                selectedYear = state.displayedMonth.year,
                selectedMonth = state.displayedMonth.monthValue,
                onConfirm = { year, month ->
                    val newYearMonth = YearMonth.of(year, month)
                    onEvent(CalendarEvent.MonthChanged(newYearMonth))
                },
                onDismiss = { showMonthYearPicker = false }
            )
        }
    }
}