package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.amirnlz.tasko.core.ui.theme.CalendarTheme

@Composable
fun CalendarDatePicker(
    state: CalendarState,
    modifier: Modifier = Modifier,
    onEvent: (CalendarEvent) -> Unit = {}
) {
    val listState = rememberLazyListState()
    var todayLineOffset by remember { mutableFloatStateOf(0f) }
    var todayLineWidth by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current

    // Scroll to today on first composition
    LaunchedEffect(Unit) {
        val todayIndex = state.days.indexOfFirst { it.isToday }
        if (todayIndex != -1) {
            listState.scrollToItem(
                index = todayIndex,
                scrollOffset = -(density.run { 100.dp.toPx() }).toInt()
            )
        }
    }

    Box(modifier = modifier) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.days) { day ->
                CalendarDayItem(
                    day = day,
                    isSelected = day.date == state.selectedDate,
                    modifier = Modifier
                        .onGloballyPositioned { layoutCoordinates ->
                            if (day.isToday) {
                                val position = layoutCoordinates.localToRoot(Offset.Zero)
                                todayLineOffset = position.x
                                todayLineWidth = layoutCoordinates.size.width.toFloat()
                            }
                        },
                    onClick = { onEvent(CalendarEvent.DateSelected(day.date)) }
                )
            }
        }

        // Thin bottom line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
                .align(Alignment.BottomCenter)
        )

        // Today's thick indicator line
        if (todayLineWidth > 0) {
            val lineWidth = with(density) { todayLineWidth.toDp() }
            val lineOffset = with(density) { todayLineOffset.toDp() }
            Box(
                modifier = Modifier
                    .offset(x = lineOffset)
                    .width(lineWidth)
                    .height(3.dp)
                    .background(CalendarTheme.todayTextColor())
                    .align(Alignment.BottomCenter)
            )
        }
    }
}