package com.derar.libya.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.derar.libya.todoapp.data.ToDoDao
import com.derar.libya.todoapp.data.models.ToDoData


class ToDoRepository(
    private val toDoDao: ToDoDao
) {

    val getAllData: LiveData<List<ToDoData>> =toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }


}