package com.reboot.todo.database

import androidx.lifecycle.LiveData
import com.reboot.todo.models.TodoModel

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<TodoModel>> = todoDao.getAllTodos()

    suspend fun insert(todo: TodoModel){
        todoDao.insert(todo)
    }

    suspend fun delete(todo: TodoModel){
        todoDao.delete(todo)
    }

    suspend fun update(todo: TodoModel){
        todoDao.update(todo.id, todo.title, todo.desc,todo.dueDate)
    }
}