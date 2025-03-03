package com.amirnlz.tasko.home.domain.repository

import com.amirnlz.tasko.home.domain.model.TodoTask
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TasksRepository {
    fun getAllTodoTasks(): Flow<List<TodoTask>>

    suspend fun addTask(todoTask: TodoTask)

    fun getTasksByDate(date: LocalDate): Flow<List<TodoTask>>

}