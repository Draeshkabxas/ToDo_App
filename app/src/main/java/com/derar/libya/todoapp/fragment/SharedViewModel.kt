package com.derar.libya.todoapp.fragment

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.Priority

class SharedViewModel(application: Application) :
    AndroidViewModel(application) {
/**
 * This listener changing the text color of spinner item
 * High Priority will change to red
 * Medium Priority will change to yellow
 * Low Priority will change to green
 */
    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when (position) {
                0 -> changeTextColor(application, parent,  R.color.red)
                1 -> changeTextColor(application, parent,  R.color.yellow)
                2 -> changeTextColor(application, parent,  R.color.green)
            }
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }


    /**
     * This function changing the text color of spinner item
     * of pass position to the color that have same id in color resource file
     * @param application
     * @param parent is the adapterView that in spinner
     * @param id the id color in color resource file
     */
    private fun changeTextColor(
        application: Application,
        parent: AdapterView<*>?,
        id: Int
    ) {
        (parent?.getChildAt(0) as TextView)
            .setTextColor(
                ContextCompat.getColor(application, id)
            )
    }


    /**
     * This function covert spinner selected item to priority
     */
    fun parsePriority(priority: String): Priority =
        when (priority) {
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
    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }



    fun parsePriorityToInt(priority: Priority):Int = when(priority){
        Priority.HIGH -> 0
        Priority.MEDIUM -> 1
        Priority.LOW -> 2
    }
}