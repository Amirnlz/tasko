package com.amirnlz.tasko.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amirnlz.tasko.home.domain.model.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks ORDER by dueDateMillis DESC")
    fun getAllTasks(): Flow<List<TodoTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(todoTask: TodoTask)


    @Query("SELECT * FROM tasks WHERE dueDateMillis = :millis")
    fun getTasksByDate(millis: Long): Flow<List<TodoTask>>
}