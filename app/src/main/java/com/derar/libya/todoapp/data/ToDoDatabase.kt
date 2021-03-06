package com.derar.libya.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.derar.libya.todoapp.data.models.ToDoData

@Database(
    entities = [ToDoData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun toDoDao():ToDoDao

    companion object{

        const val DATABASE_NAME= "todo_database"

        @Volatile
        private var INSTANCE:ToDoDatabase? = null

        fun getDatabase(context: Context):ToDoDatabase{
            val tempInstance= INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}