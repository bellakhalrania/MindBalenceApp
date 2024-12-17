package iset.example.mindbalenceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iset.example.mindbalenceapp.databinding.TaskItemBinding
import iset.example.mindbalenceapp.models.Task

class TaskAdapter(private val taskList: MutableList<Task>?) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder utilisant le ViewBinding pour une vue plus claire
    class TaskViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // Création de la vue avec ViewBinding
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Récupérer la tâche actuelle
        val currentTask = taskList?.get(position)

        // Assigner les valeurs aux TextView
        currentTask?.let { task ->
            holder.binding.taskTitleTextView.text = task.name
            holder.binding.taskDateTextView.text = "Date: ${task.date}"
            holder.binding.taskTimeTextView.text = "Time: ${task.time}"
        }
    }

    override fun getItemCount(): Int {
        // Retourner la taille de la liste ou 0 si null
        return taskList?.size ?: 0
    }
}
