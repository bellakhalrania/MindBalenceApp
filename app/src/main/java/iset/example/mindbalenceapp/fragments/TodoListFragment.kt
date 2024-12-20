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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iset.example.mindbalenceapp.R
import iset.example.mindbalenceapp.adapters.TaskAdapter
import iset.example.mindbalenceapp.models.Task
import iset.example.mindbalenceapp.ui.ApiClientTask
import kotlinx.coroutines.launch
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
                showEditDialog(task)
            }

            override fun onDeleteTask(task: Task) {
                deleteTask(task)
            }

            override fun onTaskCompleted(task: Task) {
                completeTask(task)
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
                val task = Task(null, taskName, selectedDate, selectedTime, isCompleted = false)
                addTask(task)

                // Reset inputs
                taskInput.setText("")
                datePickerButton.text = "Pick Date"
                timePickerButton.text = "Pick Time"
                selectedDate = ""
                selectedTime = ""
            }
        }

        // Load tasks from API
        loadTasks()
    }

    private fun loadTasks() {
        lifecycleScope.launch {
            try {
                val tasks = ApiClientTask.instance.getAllTask()
                taskList.clear()
                taskList.addAll(tasks)
                taskAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e("TodoListFragment", "Error loading tasks: ${e.message}")
            }
        }
    }

    private fun addTask(task: Task) {
        lifecycleScope.launch {
            try {
                val newTask = ApiClientTask.instance.addTask(task)
                taskList.add(newTask)
                taskAdapter.notifyItemInserted(taskList.size - 1)
            } catch (e: Exception) {
                Log.e("TodoListFragment", "Error adding task: ${e.message}")
            }
        }
    }

    private fun deleteTask(task: Task) {
        lifecycleScope.launch {
            try {
                task.id?.let { ApiClientTask.instance.deleteTask(it.toInt().toString()) }
                val position = taskList.indexOf(task)
                taskList.removeAt(position)
                taskAdapter.notifyItemRemoved(position)
            } catch (e: Exception) {
                Log.e("TodoListFragment", "Error deleting task: ${e.message}")
            }
        }
    }

    private fun completeTask(task: Task) {
        lifecycleScope.launch {
            try {
                task.id?.let {
                    val updatedTask = ApiClientTask.instance.completeTask(it.toInt())
                    val position = taskList.indexOf(task)
                    taskList[position] = updatedTask
                    taskAdapter.notifyItemChanged(position)
                }
            } catch (e: Exception) {
                Log.e("TodoListFragment", "Error completing task: ${e.message}")
            }
        }
    }

    private fun showEditDialog(task: Task) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_task, null)
        val taskNameInput = dialogView.findViewById<EditText>(R.id.taskNameInput)
        val dateInput = dialogView.findViewById<Button>(R.id.taskDateButton)
        val timeInput = dialogView.findViewById<Button>(R.id.taskTimeButton)
        val cancelBtn = dialogView.findViewById<Button>(R.id.cancel_button)
        val saveBtn = dialogView.findViewById<Button>(R.id.confirm_button)

        taskNameInput.setText(task.name)
        dateInput.text = task.date
        timeInput.text = task.time

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit Task")
            .setView(dialogView)
            .create()

        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateInput.text = formattedDate
            }, year, month, day).show()
        }

        timeInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                timeInput.text = formattedTime
            }, hour, minute, true).show()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        saveBtn.setOnClickListener {
            val newTaskName = taskNameInput.text.toString()
            val newDate = dateInput.text.toString()
            val newTime = timeInput.text.toString()

            if (newTaskName.isEmpty() || newDate.isEmpty() || newTime.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                task.name = newTaskName
                task.date = newDate
                task.time = newTime
                updateTask(task)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun updateTask(task: Task) {
        lifecycleScope.launch {
            try {
                task.id?.let {
                    val updatedTask = ApiClientTask.instance.updateTask(it.toInt().toString(), task)

                    val position = taskList.indexOfFirst { t -> t.id == updatedTask.id }
                    taskList[position] = updatedTask
                    taskAdapter.notifyItemChanged(position)
                }
            } catch (e: Exception) {
                Log.e("TodoListFragment", "Error updating task: ${e.message}")
            }
        }
    }
}
