package com.amirnlz.tasko.home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TodoTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val dueDateMillis: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
)
