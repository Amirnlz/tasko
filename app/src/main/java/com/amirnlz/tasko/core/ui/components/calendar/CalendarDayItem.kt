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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amirnlz.tasko.core.ui.theme.CalendarTheme

@Composable
fun CalendarDayItem(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    val fontWeight: FontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal

    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.secondaryContainer
        else -> Color.Transparent
    }

    val textColor = when {
        isSelected -> CalendarTheme.todayTextColor()
        day.isPast -> CalendarTheme.pastDateColor()
        else -> CalendarTheme.futureDateColor()
    }

    val appliedTextColor =
        textColor.copy(alpha = if (day.isPast) 0.7f else 1f)



    Column(
        modifier = modifier
            .width(48.dp)
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            day.dayOfMonth.toString(),
            fontSize = 18.sp,
            fontWeight = fontWeight,
            color = appliedTextColor,
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            day.dayOfWeek,
            color = appliedTextColor,
            fontSize = 15.sp,
            fontWeight = fontWeight,
        )
    }
}