package com.amirnlz.tasko.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amirnlz.tasko.home.ui.component.HomeAddingTodoBottomSheet
import com.amirnlz.tasko.home.ui.component.HomeCalendar
import com.amirnlz.tasko.home.ui.component.HomeTopBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = koinViewModel(),
    taskViewModel: TasksViewModel = koinViewModel(),
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val calendarState by calendarViewModel.state.collectAsStateWithLifecycle()
    val tasksUiState by taskViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(calendarState.selectedDate) {
        taskViewModel.onEvent(TasksEvent.GetTasksByDate(calendarState.selectedDate))
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        topBar = { HomeTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
                scope.launch {
                    sheetState.show()
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            if (showBottomSheet)
                HomeAddingTodoBottomSheet(
                    onDismissRequest = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    sheetState = sheetState,
                )
            HomeCalendar(viewModel = calendarViewModel)
            when (tasksUiState) {
                is TasksUiState.Success -> Text((tasksUiState as TasksUiState.Success).tasks.size.toString())
                is TasksUiState.Error -> Text(tasksUiState.toString())
                is TasksUiState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

