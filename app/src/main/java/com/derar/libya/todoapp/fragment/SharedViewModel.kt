package com.derar.libya.todoapp.fragment

import android.app.Application
import android.content.res.Resources
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.derar.libya.todoapp.MainActivity
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.Priority

class SharedViewModel(application: Application):
    AndroidViewModel(application) {
    /**
     * This function covert spinner selected item to priority
     */
     fun parsePriority(priority: String): Priority =
         when (priority)
        {
            "High Priority" -> Priority.HIGH

            "Medium Priority" -> Priority.MEDIUM

            "Low Priority" -> Priority.LOW

            else -> Priority.LOW
        }


    /**
     * This function check if title and description not empty
     * @return true  if title and description not empty
     * @return false if title or description is empty
     */
     fun verifyDataFromUser(title: String, description: String):Boolean{
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            false
        }else !(title.isEmpty() || description.isEmpty())
    }
}