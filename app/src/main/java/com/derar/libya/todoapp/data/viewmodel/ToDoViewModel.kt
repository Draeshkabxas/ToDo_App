package com.derar.libya.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.derar.libya.todoapp.data.ToDoDatabase
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application)
    :AndroidViewModel(application) {

        private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()

    private val repository: ToDoRepository


    val getAllData: LiveData<List<ToDoData>>


    init {
        repository=ToDoRepository(toDoDao)
        getAllData=repository.getAllData
    }

     fun insertData(toDoData: ToDoData){
      viewModelScope.launch(IO) {
          repository.insertData(toDoData)
      }
    }


    fun updateData(toDoData:ToDoData){
        viewModelScope.launch(IO){
            repository.updateData(toDoData)
        }
    }

}