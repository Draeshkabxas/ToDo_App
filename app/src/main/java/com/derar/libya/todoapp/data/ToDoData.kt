package com.derar.libya.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TODO_TABLE_NAME="todo_table"

@Entity(tableName = TODO_TABLE_NAME)
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var description:String,
    var priority: Priority
)