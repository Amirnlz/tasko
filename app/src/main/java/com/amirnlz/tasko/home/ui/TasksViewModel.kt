package com.amirnlz.tasko.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirnlz.tasko.home.domain.model.TodoTask
import com.amirnlz.tasko.home.domain.repository.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate


sealed interface TasksEvent {
    data object GetAllTasks : TasksEvent
    data class GetTasksByDate(val date: LocalDate) : TasksEvent
    data class AddTask(val todoTask: TodoTask) : TasksEvent
}

sealed interface TasksUiState {
    data object Loading : TasksUiState
    data class Success(val tasks: List<TodoTask>) : TasksUiState
    data class Error(val e: Throwable? = null) : TasksUiState
}


class TasksViewModel(private val repository: TasksRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()


    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.GetAllTasks -> getAllTasks()
            is TasksEvent.GetTasksByDate -> getTasksByDate(event.date)
            is TasksEvent.AddTask -> addTask(event.todoTask)
        }
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            _uiState.value = TasksUiState.Loading
            try {
                repository.getAllTodoTasks().collect { data ->
                    _uiState.value =
                        TasksUiState.Success(data)

                }
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e)

            }
        }
    }

    private fun getTasksByDate(date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = TasksUiState.Loading
            try {
                repository.getTasksByDate(date).collect { data ->
                    _uiState.value =
                        TasksUiState.Success(data)

                }
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e)

            }
        }
    }

    private fun addTask(todoTask: TodoTask) {
        viewModelScope.launch {
            repository.addTask(todoTask)
        }
    }
}