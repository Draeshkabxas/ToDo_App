package com.derar.libya.todoapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val TODO_TABLE_NAME="todo_table"

@Entity(tableName = TODO_TABLE_NAME)
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var description:String,
    var priority: Priority
): Parcelable