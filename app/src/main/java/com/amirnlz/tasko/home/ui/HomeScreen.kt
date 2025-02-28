package com.amirnlz.tasko.home.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amirnlz.tasko.core.ui.components.calendar.CalendarDatePicker
import com.amirnlz.tasko.core.ui.components.calendar.CalendarDates
import com.amirnlz.tasko.core.ui.components.calendar.CalendarEvent
import com.amirnlz.tasko.core.ui.components.calendar.CalendarMonthYearPicker
import com.amirnlz.tasko.core.ui.components.calendar.CalendarState
import com.amirnlz.tasko.core.ui.theme.TaskoTheme
import com.amirnlz.tasko.home.ui.component.GreetingMessage

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val calendarState = remember {
        CalendarState(
            days = CalendarDates.generateMonthDates()
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        topBar = { GreetingMessage() }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            CalendarMonthYearPicker(state = calendarState) {
                Log.d(
                    "MONTH-YEAR-PICKER",
                    "HomeScreen: ${if (it is CalendarEvent.MonthChanged) it.yearMonth else "OTHER-THING"}"
                )
            }
            CalendarDatePicker(
                state = calendarState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(80.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    TaskoTheme {
        HomeScreen()
    }
}

