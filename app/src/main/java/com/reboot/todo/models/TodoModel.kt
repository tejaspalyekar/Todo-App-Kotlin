package com.reboot.todo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoModel(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "created_date") val createdDate: String,
    @ColumnInfo(name = "due_date") val dueDate: String
): java.io.Serializable
