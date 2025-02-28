package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amirnlz.tasko.core.ui.theme.CalendarTheme

@Composable
fun CalendarDayItem(
    day: CalendarDay,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val textColor = when {
        day.isToday -> CalendarTheme.todayTextColor()
        day.isPast -> CalendarTheme.pastDateColor()
        else -> CalendarTheme.futureDateColor()
    }

    Column(
        modifier = modifier
            .width(48.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(enabled = !day.isPast) { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            fontSize = 18.sp,
            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .background(
                    color = when {
                        isSelected -> MaterialTheme.colorScheme.secondaryContainer
                        day.isToday -> CalendarTheme.todayBackground()
                        else -> Color.Transparent
                    },
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = day.dayOfWeek,
            color = textColor.copy(alpha = if (day.isPast) 0.7f else 1f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}