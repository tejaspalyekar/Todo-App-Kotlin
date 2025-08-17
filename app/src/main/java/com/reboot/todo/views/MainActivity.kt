package com.reboot.todo.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.reboot.todo.R
import com.reboot.todo.adapters.TodoListAdapter
import com.reboot.todo.models.TodoModel
import com.reboot.todo.view_models.TodoViewModel

class MainActivity : AppCompatActivity(), TodoListAdapter.TodoClickListener {
    private lateinit var addTodoBtn: FloatingActionButton
    private lateinit var title: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoListAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        addTodoBtn = findViewById(R.id.floatingActionButton)
        title = findViewById(R.id.todo_title)
        recyclerView = findViewById(R.id.todo_list)


        setupRecyclerView()


        todoViewModel.allTodo.observe(this) { todos ->
            if (todos.isEmpty()) {
                title.text = "No Pending Tasks"
            } else {
                title.text = "My To-Do Bucket (${todos.size})"
            }
            todoAdapter.updateList(todos)
        }

        addTodoBtn.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoListAdapter(this, this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }

    override fun onItemClicked(todo: TodoModel) {

        val intent = Intent(this, AddTask::class.java)
        intent.putExtra("todo", todo)
        startActivity(intent)
    }

    override fun onDeleteClicked(todo: TodoModel) {

        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete '${todo.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                todoViewModel.deleteTodo(todo)
                Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onResume() {
        super.onResume()

        todoViewModel.allTodo.value?.let { todos ->
            if (todos.isEmpty()) {
                title.text = "No Pending Tasks"
            } else {
                title.text = "My To-Do Bucket (${todos.size})"
            }
        }
    }
}