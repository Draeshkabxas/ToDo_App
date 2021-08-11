package com.derar.libya.todoapp.fragments


import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.Priority
import com.derar.libya.todoapp.data.models.Priority.*
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {


    companion object {

        /**
         * Change the fragment to addFragment
         * when user click on add floating button
         */
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        /**
         * This function change the visibility of passed view
         * IF passed pram is ture to set visibility to visible
         * IF passed pram is false to set visibility to invisible
         * @param emptyDatabase the situation of the database is it empty or not
         */
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            if (emptyDatabase.value!!)
                view.visibility = View.VISIBLE
            else
                view.visibility = View.INVISIBLE

        }


        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                HIGH -> view.setSelection(0)
                MEDIUM -> view.setSelection(1)
                LOW -> view.setSelection(2)
            }
        }

        /**
         * This function changing the passed card color background by passed priority
         * High Priority will change card background to red
         * Medium Priority will change background to yellow
         * Low Priority will change background to green
         */
        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView:CardView,priority: Priority){
            when (priority){
                HIGH -> setCardColor(cardView,R.color.red)
                MEDIUM -> setCardColor(cardView,R.color.yellow)
                LOW -> setCardColor(cardView,R.color.green)
            }
        }

        /**
         * This function set the passed color to be passed card background color
         * @param color the new color of the card
         * @param cardView the card that it's background will change
         */
        private fun setCardColor(cardView: CardView,color: Int) {
            cardView.setCardBackgroundColor(
                cardView.context!!.getColor(color)
            )
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view:ConstraintLayout,toDoData: ToDoData){
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoData)
                view.findNavController().navigate(action)
            }
        }
    }




}