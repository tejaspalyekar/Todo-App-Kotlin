package com.reboot.todo.views
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.reboot.todo.R
import java.text.SimpleDateFormat
import java.util.*

class AddTask : AppCompatActivity() {
    private lateinit var saveBtn : FloatingActionButton
    private lateinit var taskNameField:TextInputEditText
    private lateinit var  taskDescriptionField : TextInputEditText
    private lateinit var dateField: TextView
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        saveBtn = findViewById(R.id.save_task_btn)
        taskNameField = findViewById(R.id.task_name_field)
        taskDescriptionField = findViewById(R.id.task_description_field)
        dateField = findViewById(R.id.due_date_field)

        dateField.setOnClickListener{
            showDatePicker()
        }

        saveBtn.setOnClickListener{
            val name = taskNameField.text?.trim()
            val description = taskDescriptionField.text?.trim()

            Toast.makeText(this,"title $name desc$description",Toast.LENGTH_LONG).show()
        }
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
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }
}