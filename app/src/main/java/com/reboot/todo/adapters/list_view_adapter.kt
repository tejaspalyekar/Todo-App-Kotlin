package com.reboot.todo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reboot.todo.R
import com.reboot.todo.models.TodoModel


class TodoListAdapter(private val context: Context,val listener: TodoClickListener):
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    private val todoList = ArrayList<TodoModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListAdapter.TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoListAdapter.TodoViewHolder, position: Int) {
        val item = todoList[position]
        holder.taskName.text = item.title
        holder.taskName.isSelected = true
        holder.desc.text = item.desc
//        holder.date.text = item.createdDate
        holder.dueDate.text = item.dueDate
        holder.dueDate.isSelected = true
        holder.todo_layout.setOnClickListener {
            listener.onItemClicked(todoList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<TodoModel>){
        todoList.clear()
        todoList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
val todo_layout: ListView = itemView.findViewById(R.id.todo_list)
        val taskName = itemView.findViewById<TextView>(R.id.task_name_field)
        val desc = itemView.findViewById<TextView>(R.id.task_description_field)
        val dueDate = itemView.findViewById<TextView>(R.id.due_date_field)
    }

    interface TodoClickListener {
        fun onItemClicked(todo: TodoModel)
    }
}