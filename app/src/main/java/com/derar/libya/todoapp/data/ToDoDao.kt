package com.derar.libya.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.derar.libya.todoapp.data.models.TODO_TABLE_NAME
import com.derar.libya.todoapp.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM $TODO_TABLE_NAME ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData:ToDoData)

    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

}