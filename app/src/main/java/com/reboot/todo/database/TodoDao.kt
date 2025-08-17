package com.reboot.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.reboot.todo.models.TodoModel

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoModel)

    @Delete
    suspend fun delete(todo: TodoModel)

    @Query("SELECT * from todo_table order by id ASC")
    fun getAllTodos(): LiveData<List<TodoModel>>

    @Query("UPDATE todo_table set title = :title, `desc` = :desc, due_date = :dueDate where id = :id")
    suspend fun update(id: Int?, title: String?, desc: String?, dueDate: String?)
}