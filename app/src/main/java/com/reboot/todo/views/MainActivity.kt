package com.reboot.todo.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.reboot.todo.R

class MainActivity : AppCompatActivity() {
    private lateinit var addTodoBtn: FloatingActionButton
    private lateinit var title: TextView
    private lateinit var listView:ListView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTodoBtn = findViewById(R.id.floatingActionButton)
        title = findViewById(R.id.todo_title)
        listView = findViewById(R.id.todo_list)


        if(listView.isEmpty()){

            title.text = "No Pending Tasks"

        }else{
            title.text = "My To-Do Bucket"
        }


        addTodoBtn.setOnClickListener {
            val intent = Intent(this,AddTask::class.java)
            startActivity(intent)
        }
    }


}