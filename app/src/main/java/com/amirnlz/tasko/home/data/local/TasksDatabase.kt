package com.amirnlz.tasko.home.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amirnlz.tasko.home.domain.model.TodoTask

@Database(entities = [TodoTask::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao


    companion object {
        @Volatile
        private var INSTANCE: TasksDatabase? = null

        fun getInstance(context: Context): TasksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "tasks_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }


    }

}