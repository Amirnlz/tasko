package com.amirnlz.tasko.home.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amirnlz.tasko.core.ui.theme.TaskoTheme
import com.amirnlz.tasko.home.ui.CalendarEvent
import com.amirnlz.tasko.home.ui.CalendarViewModel
import java.time.YearMonth

@Composable
fun HomeCalendar(modifier: Modifier = Modifier, viewModel: CalendarViewModel = viewModel()) {

    val state by viewModel.state

    Column(
        modifier,
    ) {
        HomeSelectedYearMonth(
            selectedYearMonth = state.displayedYearMonth,
            onHeaderClick = { year, month ->
                val newYearMonth = YearMonth.of(year, month)
                viewModel.onEvent(CalendarEvent.YearMonthChanged(newYearMonth))
            }
        )
//        CalendarDatePicker(
//            state = state,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//                .height(80.dp)
//        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeCalendarPrev() {
    TaskoTheme {
        HomeCalendar()
    }
}