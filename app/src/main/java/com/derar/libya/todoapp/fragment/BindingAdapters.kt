package com.derar.libya.todoapp.fragment


import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.derar.libya.todoapp.R
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
        fun emptyDatabase(view:View , emptyDatabase : MutableLiveData<Boolean>){
                if(emptyDatabase.value!!)
                    view.visibility = View.VISIBLE
                else
                    view.visibility = View.INVISIBLE

        }


    }

}