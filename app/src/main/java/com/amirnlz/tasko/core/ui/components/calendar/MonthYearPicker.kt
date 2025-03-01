package com.amirnlz.tasko.core.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amirnlz.tasko.core.ui.components.WheelPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthYearPicker(
    selectedYear: Int,
    selectedMonth: Int,
    onConfirm: (year: Int, month: Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedYearState by remember { mutableIntStateOf(selectedYear) }
    var selectedMonthState by remember { mutableIntStateOf(selectedMonth) }

    val years = remember { CalendarConstants.getYearsRange() }
    val months = remember { CalendarConstants.months }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.8f)
                        .height(40.dp) // must match itemHeight in WheelPicker
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Year Picker
                    WheelPicker(
                        modifier = Modifier.weight(1f),
                        items = years,
                        initialIndex = years.indexOf(selectedYearState).coerceAtLeast(0),
                        visibleCount = 5,
                        itemHeight = 40,
                        onSelected = { index, _ ->
                            selectedYearState = years[index]
                        },
                    )

                    // Month Picker
                    WheelPicker(
                        modifier = Modifier.weight(1f),
                        items = months,
                        initialIndex = (selectedMonthState - 1).coerceIn(0, months.lastIndex),
                        visibleCount = 5,
                        itemHeight = 40,
                        onSelected = { index, _ ->
                            selectedMonthState = index + 1
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onConfirm(selectedYearState, selectedMonthState)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        }
    }
}
