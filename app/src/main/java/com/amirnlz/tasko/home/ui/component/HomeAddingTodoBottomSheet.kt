package com.amirnlz.tasko.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.amirnlz.tasko.home.domain.model.TodoTask
import com.amirnlz.tasko.home.ui.TasksEvent
import com.amirnlz.tasko.home.ui.TasksViewModel
import com.amirnlz.tasko.utils.toLocalDateFromMillis
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAddingTodoBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    tasksViewModel: TasksViewModel = koinViewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,

        ) {
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Title") },
                placeholder = { Text("") },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )
            Box(
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable {
                        showDatePicker = true
                    }
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.DateRange, contentDescription = "")
                    Text(date.toString())
                }
            }
            if (showDatePicker)
                DatePickerModal(onDateSelected = { value ->
                    showDatePicker = false
                    date = value.toLocalDateFromMillis() ?: LocalDate.now()
                },
                    onDismiss = {
                        showDatePicker = false
                    }
                )


            Button(
                onClick = {
                    val currentTimestamp = System.currentTimeMillis()
                    val task = TodoTask(title = title, dueDateMillis = currentTimestamp)
                    tasksViewModel.onEvent(TasksEvent.AddTask(task))
                    scope.launch {
                        sheetState.hide()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(16.dp),
            ) { Text("Add Task") }
        }
    }
}