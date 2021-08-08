package com.derar.libya.todoapp.fragment.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.Priority
import com.derar.libya.todoapp.data.models.Priority.*
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    /**
     * This is ToDoData list that will show in recycle view
     */
    private var dataList = emptyList<ToDoData>()

    class ViewHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDoData) {
            with(binding) {
                titleTxt.text = item.title
                descriptionTxt.text = item.description
                val priority = item.priority
               setCardPriorityIndicatorColor(priority)
            }
        }

        /**
         * This function changing the card color by priority
         * High Priority will change to red
         * Medium Priority will change to yellow
         * Low Priority will change to green
         */
        private fun setCardPriorityIndicatorColor(priority: Priority) {
            when (priority){
                HIGH -> setCardColor(R.color.red)
                MEDIUM -> setCardColor(R.color.yellow)
                LOW -> setCardColor(R.color.green)
            }
        }

        /**
         * This function set the passed color to be card priorityIndicator color
         * @param newColor the new color of the card
         */
        private fun setCardColor(newColor: Int) {
            binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,newColor))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ListAdapter.ViewHolder(
            RowLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    /**
     * this function set dataList of adapter to be the passed list
     * @param toDoData the dataList that will be setting in adapter
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setData(toDoData: List<ToDoData>){
        this.dataList = toDoData
        notifyDataSetChanged()
    }


}