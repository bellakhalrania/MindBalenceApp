package iset.example.mindbalenceapp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
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
        taskAdapter = TaskAdapter(taskList)
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
                val task = Task(taskName, selectedDate, selectedTime)
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
}
