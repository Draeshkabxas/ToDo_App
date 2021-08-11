package com.derar.libya.todoapp.fragments.list.adapter

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    /**
     * This is ToDoData list that will show in recycle view
     */
     var dataList = emptyList<ToDoData>()

    class ViewHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
        val toDoDiffUtil = ToDoDiffUtil(this.dataList,toDoData)
        val toDoDiffUtilResult= DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
       toDoDiffUtilResult.dispatchUpdatesTo(this)
    }


}