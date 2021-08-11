package com.derar.libya.todoapp.fragments.list

import android.annotation.SuppressLint

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
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
        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val currentItem=dataList[position]
        holder.bind(currentItem)
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