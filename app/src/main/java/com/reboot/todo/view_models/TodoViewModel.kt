package com.reboot.todo.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.reboot.todo.database.TodoDatabase
import com.reboot.todo.database.TodoRepository
import com.reboot.todo.models.TodoModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TodoRepository
    val allTodo : LiveData<List<TodoModel>>

    init {
        val dao = TodoDatabase.getDatabase(application).getTodoDao()
        repository = TodoRepository(dao)
        allTodo = repository.allTodos
    }

    fun insertTodo(todo: TodoModel) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(todo)
    }

    fun updateTodo(todo: TodoModel) = viewModelScope.launch(Dispatchers.IO){
        repository.update(todo)
    }

    fun deleteTodo(todo: TodoModel) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(todo)
    }
}