package com.amirnlz.tasko.home

import com.amirnlz.tasko.home.data.local.TasksDao
import com.amirnlz.tasko.home.data.local.TasksDatabase
import com.amirnlz.tasko.home.data.repository.TasksRepositoryImpl
import com.amirnlz.tasko.home.domain.repository.TasksRepository
import com.amirnlz.tasko.home.ui.CalendarViewModel
import com.amirnlz.tasko.home.ui.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModules = module {
    single<TasksDatabase> { TasksDatabase.getInstance(get()) }
    single<TasksDao> { get<TasksDatabase>().tasksDao() }
    single<TasksRepository> { TasksRepositoryImpl(get()) }
    viewModelOf(::TasksViewModel)
    viewModelOf(::CalendarViewModel)
}