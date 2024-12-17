package iset.example.mindbalenceapp.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iset.example.mindbalenceapp.R
import iset.example.mindbalenceapp.adapters.TaskAdapter
import iset.example.mindbalenceapp.models.Task
import java.util.Calendar

class TodoListFragment : Fragment() {
    private val taskList: MutableList<Task> = mutableListOf()
    private lateinit var taskAdapter: TaskAdapter
    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        val taskRecyclerView = view.findViewById<RecyclerView>(R.id.taskRecyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskAdapter = TaskAdapter(taskList, object : TaskAdapter.OnTaskActionListener {
            override fun onEditTask(task: Task) {
                // Log pour vérifier si la méthode est appelée
                Log.d("TodoListFragment", "onEditTask called for task: ${task.name}")
                showEditDialog(task)
            }


            override fun onDeleteTask(task: Task) {
                // Delete task logic
                taskList.remove(task)
                taskAdapter.notifyDataSetChanged()
            }

            override fun onTaskCompleted(task: Task) {
                // Mark task as completed (strikethrough)
                task.isCompleted = true
                taskAdapter.notifyDataSetChanged()
            }
        })
        taskRecyclerView.adapter = taskAdapter

        // Input field
        val taskInput = view.findViewById<EditText>(R.id.taskInput)

        // Buttons
        val datePickerButton = view.findViewById<Button>(R.id.datePickerButton)
        val timePickerButton = view.findViewById<Button>(R.id.timePickerButton)
        val addTaskButton = view.findViewById<Button>(R.id.addTaskButton)

        // Date Picker Logic
        datePickerButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                datePickerButton.text = selectedDate
            }, year, month, day).show()
        }

        // Time Picker Logic
        timePickerButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                timePickerButton.text = selectedTime
            }, hour, minute, true).show()
        }

        // Add Task Logic
        addTaskButton.setOnClickListener {
            val taskName = taskInput.text.toString().trim()

            if (taskName.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val task = Task(taskName, selectedDate, selectedTime, isCompleted = false)
                taskList.add(task)
                taskAdapter.notifyItemInserted(taskList.size - 1)

                // Reset inputs
                taskInput.setText("")
                datePickerButton.text = "Pick Date"
                timePickerButton.text = "Pick Time"
                selectedDate = ""
                selectedTime = ""
            }
        }
    }

    private fun showEditDialog(task: Task) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_task, null)
        val taskNameInput = dialogView.findViewById<EditText>(R.id.taskNameInput)
        val dateInput = dialogView.findViewById<Button>(R.id.taskDateButton)  // Button for date
        val timeInput = dialogView.findViewById<Button>(R.id.taskTimeButton)  // Button for time
        val cancelBtn = dialogView.findViewById<ImageButton>(R.id.cancledbutton)
        val saveBtn = dialogView.findViewById<ImageButton>(R.id.confirm_button)

        // Pre-fill the dialog with the task's current data
        taskNameInput.setText(task.name)
        dateInput.text = task.date
        timeInput.text = task.time

        // Create the dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit Task")
            .setView(dialogView)
            .create()

        // Date button: Show DatePickerDialog
        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // Format and set the selected date
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateInput.text = formattedDate
            }, year, month, day).show()
        }

        // Time button: Show TimePickerDialog
        timeInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                // Format and set the selected time
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                timeInput.text = formattedTime
            }, hour, minute, true).show()
        }

        // Cancel button: closes the dialog without making changes
        cancelBtn.setOnClickListener {
            dialog.dismiss()  // Close the dialog
        }

        // Save button: saves the changes and updates the task in the list
        saveBtn.setOnClickListener {
            val newTaskName = taskNameInput.text.toString()
            val newDate = dateInput.text.toString()
            val newTime = timeInput.text.toString()

            // Check if any of the fields are empty before saving
            if (newTaskName.isEmpty() || newDate.isEmpty() || newTime.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Update the task with new values
                task.name = newTaskName
                task.date = newDate
                task.time = newTime

                // Notify the adapter that the task has been updated
                val position = taskList.indexOf(task)
                taskAdapter.notifyItemChanged(position)

                // Close the dialog
                dialog.dismiss()
            }
        }

        // Show the dialog
        dialog.show()
    }



}
