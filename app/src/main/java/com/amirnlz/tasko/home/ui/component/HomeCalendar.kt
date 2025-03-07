package com.amirnlz.tasko.home.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amirnlz.tasko.core.ui.components.calendar.MonthDaysPicker
import com.amirnlz.tasko.home.ui.CalendarEvent
import com.amirnlz.tasko.home.ui.CalendarViewModel
import com.kizitonwose.calendar.core.yearMonth
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth

@Composable
fun HomeCalendar(modifier: Modifier = Modifier, viewModel: CalendarViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier,
    ) {
        HomeSelectedYearMonth(
            selectedYearMonth = state.selectedDate.yearMonth,
            onHeaderClick = { year, month ->
                val newYearMonth = YearMonth.of(year, month)
                viewModel.onEvent(CalendarEvent.YearMonthChanged(newYearMonth))
            }
        )
        MonthDaysPicker(
            state = state,
            onSelected = { date ->
                viewModel.onEvent(CalendarEvent.DayChanged(date))
            },
        )
    }
}