package com.derar.libya.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.derar.libya.todoapp.data.TODO_TABLE_NAME
import com.derar.libya.todoapp.data.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM $TODO_TABLE_NAME ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

}