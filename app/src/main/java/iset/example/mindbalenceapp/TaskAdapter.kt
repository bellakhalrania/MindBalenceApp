package iset.example.mindbalenceapp.adapters

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iset.example.mindbalenceapp.R
import iset.example.mindbalenceapp.models.Task

class TaskAdapter(
    private val taskList: MutableList<Task>,
    private val taskActionListener: OnTaskActionListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface OnTaskActionListener {
        fun onEditTask(task: Task)
        fun onDeleteTask(task: Task)
        fun onTaskCompleted(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = taskList.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val taskDate: TextView = itemView.findViewById(R.id.taskDateTextView)
        private val taskTime: TextView = itemView.findViewById(R.id.taskTimeTextView)
        private val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)
        private val editIcon: ImageView = itemView.findViewById(R.id.editIcon)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)

        fun bind(task: Task) {
            taskTitle.text = task.name
            taskDate.text = task.date
            taskTime.text = task.time

            // Check the task completion state
            taskCheckBox.isChecked = task.isCompleted
            if (task.isCompleted) {
                taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                taskTitle.setTextColor(Color.GRAY)
            } else {
                taskTitle.paintFlags = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                taskTitle.setTextColor(Color.BLACK)
            }

            taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    taskActionListener.onTaskCompleted(task)
                }
            }

            // Handle the edit icon click
            editIcon.setOnClickListener {
                Log.d("TaskAdapter", "Edit icon clicked for task: ${task.name}")
                taskActionListener.onEditTask(task)
            }

            // Handle the delete icon click
            deleteIcon.setOnClickListener {
                Log.d("TaskAdapter", "Delete icon clicked for task: ${task.name}")
                taskActionListener.onDeleteTask(task)
            }
        }
    }
}
