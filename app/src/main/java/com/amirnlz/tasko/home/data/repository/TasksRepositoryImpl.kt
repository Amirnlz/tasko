package com.amirnlz.tasko.home.data.repository

import com.amirnlz.tasko.home.data.local.TasksDao
import com.amirnlz.tasko.home.domain.model.TodoTask
import com.amirnlz.tasko.home.domain.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneOffset

class TasksRepositoryImpl(private val tasksDao: TasksDao) : TasksRepository {
    override fun getAllTodoTasks(): Flow<List<TodoTask>> {
        return tasksDao.getAllTasks().flowOn(Dispatchers.IO)
    }

    override suspend fun addTask(todoTask: TodoTask) {
        return withContext(Dispatchers.IO) {
            tasksDao.addTask(todoTask)
        }
    }

    override suspend fun updateTask(todoTask: TodoTask) {
        return withContext(Dispatchers.IO) {
            tasksDao.updateTask(todoTask)
        }
    }

    override fun getTasksByDate(date: LocalDate): Flow<List<TodoTask>> {
        val millis = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        return tasksDao.getTasksByDate(millis).flowOn(Dispatchers.IO)
    }
}