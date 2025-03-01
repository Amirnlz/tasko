package com.amirnlz.tasko.home.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amirnlz.tasko.core.ui.components.calendar.CalendarConstants
import com.amirnlz.tasko.core.ui.components.calendar.MonthYearPicker
import java.time.YearMonth

@Composable
fun HomeSelectedYearMonth(
    selectedYearMonth: YearMonth,
    onHeaderClick: (year: Int, month: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMonthYearPicker by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { showMonthYearPicker = true },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = CalendarConstants.months[selectedYearMonth.monthValue - 1] +
                    " ${selectedYearMonth.year}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        IconButton(onClick = { showMonthYearPicker = true }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
            )
        }
    }

    if (showMonthYearPicker) {
        MonthYearPicker(
            selectedYear = selectedYearMonth.year,
            selectedMonth = selectedYearMonth.monthValue,
            onConfirm = onHeaderClick,
            onDismiss = { showMonthYearPicker = false }
        )
    }
}