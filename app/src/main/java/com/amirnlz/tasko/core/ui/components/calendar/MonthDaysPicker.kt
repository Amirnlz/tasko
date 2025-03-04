package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun MonthDaysPicker(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onSelected: (LocalDate) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current

//    LaunchedEffect(state) {
//        val selectedDateIndex: Int = state.days.indexOfFirst { it.date == state.selectedDate }
//        if (selectedDateIndex >= 0)
//            lazyListState.animateScrollToItem(
//                index = selectedDateIndex,
//                scrollOffset = -(density.run { 100.dp.toPx() }).toInt()
//            )
//    }

    LazyRow(
        modifier.fillMaxWidth(),
        state = lazyListState,
    ) {
        items(state.days) { day ->
            CalendarDayItem(day = day,
                isSelected = day.date == state.selectedDate,
                onClick = { onSelected(day.date) })
        }
    }

    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray.copy(alpha = 0.3f))
    )

}