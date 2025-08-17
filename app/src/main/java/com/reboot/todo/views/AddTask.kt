package com.reboot.todo.views
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.reboot.todo.R
import com.reboot.todo.models.TodoModel
import com.reboot.todo.view_models.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTask : AppCompatActivity() {
    private lateinit var saveBtn : FloatingActionButton
    private lateinit var taskNameField:TextInputEditText
    private lateinit var taskDescriptionField : TextInputEditText
    private lateinit var dateField: TextView
    private lateinit var todoViewModel: TodoViewModel
    private val calendar = Calendar.getInstance()
    private var selectedDate: String = ""
    private var isUpdate = false
    private var todoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)


        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        saveBtn = findViewById(R.id.save_task_btn)
        taskNameField = findViewById(R.id.task_name_field)
        taskDescriptionField = findViewById(R.id.task_description_field)
        dateField = findViewById(R.id.due_date_field)


        val intent = intent
        if (intent.hasExtra("todo")) {
            isUpdate = true
            val todo = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("todo", TodoModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getSerializableExtra("todo") as TodoModel
            }
            todoId = todo?.id
            taskNameField.setText(todo?.title)
            taskDescriptionField.setText(todo?.desc)
            dateField.text = "Task Due Date: ${todo?.dueDate}"
            selectedDate = todo?.dueDate ?: ""
        }

        dateField.setOnClickListener{
            showDatePicker()
        }

        saveBtn.setOnClickListener{
            saveTodo()
        }
    }

    private fun saveTodo() {
        val name = taskNameField.text?.trim().toString()
        val description = taskDescriptionField.text?.trim().toString()

        if (name.isEmpty()) {
            taskNameField.error = "Task name is required"
            return
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a due date", Toast.LENGTH_SHORT).show()
            return
        }

        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        
        val todo = if (isUpdate) {
            TodoModel(todoId, name, description, currentDate, selectedDate)
        } else {
            TodoModel(null, name, description, currentDate, selectedDate)
        }

        if (isUpdate) {
            todoViewModel.updateTodo(todo)
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            todoViewModel.insertTodo(todo)
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
        }

        finish()
    }

    @SuppressLint("SetTextI18n")
    fun showDatePicker(){
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                dateField.text = "Task Due Date: $formattedDate"
                this.selectedDate = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }
}