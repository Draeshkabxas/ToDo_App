package com.derar.libya.todoapp.data

import androidx.room.TypeConverter
import com.derar.libya.todoapp.data.Priority.*

class Converter {

    @TypeConverter
    fun fromPriority(priority:Priority):String =
       priority.name

    @TypeConverter
    fun toPriority(priority:String):Priority=
        Priority.valueOf(priority)

}