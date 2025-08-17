package com.reboot.todo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reboot.todo.R
import com.reboot.todo.models.TodoModel

class TodoListAdapter(private val context: Context, private val listener: TodoClickListener):
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    private val todoList = ArrayList<TodoModel>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList[position]
        holder.taskName.text = item.title
        holder.taskName.isSelected = true
        holder.desc.text = item.desc
        holder.dueDate.text = "Due: ${item.dueDate}"
        holder.dueDate.isSelected = true
        
        holder.itemView.setOnClickListener {
            listener.onItemClicked(todoList[holder.bindingAdapterPosition])
        }
        
        holder.deleteBtn.setOnClickListener {
            listener.onDeleteClicked(todoList[holder.bindingAdapterPosition])
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
        val taskName = itemView.findViewById<TextView>(R.id.task_name)
        val desc = itemView.findViewById<TextView>(R.id.task_description)
        val dueDate = itemView.findViewById<TextView>(R.id.due_date)
        val deleteBtn = itemView.findViewById<ImageButton>(R.id.delete_btn)
    }

    interface TodoClickListener {
        fun onItemClicked(todo: TodoModel)
        fun onDeleteClicked(todo: TodoModel)
    }
}