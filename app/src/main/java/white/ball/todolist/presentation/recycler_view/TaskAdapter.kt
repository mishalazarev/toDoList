package white.ball.todolist.presentation.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import white.ball.todolist.R
import white.ball.todolist.databinding.BlockTaskBinding
import white.ball.todolist.domain.model.Task
import white.ball.todolist.domain.contract.TaskActionListener


class TaskAdapter(
    private val taskActionListener: TaskActionListener
) : RecyclerView.Adapter<TaskAdapter.TaskHolder>(), View.OnClickListener {

    var tasksList: List<Task> = emptyList()
        set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class TaskHolder(val binding: BlockTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BlockTaskBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.deleteTaskImageButton.setOnClickListener(this)
        binding.isDoneTaskCheckBox.setOnClickListener(this)

        return TaskHolder(binding)
    }

    override fun getItemCount(): Int = tasksList.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = tasksList[position]

        holder.binding.apply {
            blockTaskLayout.tag = task
            nameTaskTextView.tag = task
            deleteTaskImageButton.tag = task
            isDoneTaskCheckBox.tag = task

            nameTaskTextView.text = task.nameTask
            isDoneTaskCheckBox.isChecked = task.isDone


            if (task.nameTask.isBlank()) {
                nameTaskTextView.hint = BACKGROUND_TEXT_FOR_EDITH_TEXT
            } else {
                nameTaskTextView.text = task.nameTask
            }
        }
    }

    override fun onClick(view: View) {
        val task: Task = view.tag as Task

        when (view.id) {
            R.id.delete_task_image_button -> taskActionListener.onRemoveTaskPressed(task)
            R.id.is_done_task_check_box -> taskActionListener.onUpDateTaskPressed(task)
            else -> taskActionListener.onLaunchDetailsAboutTaskPressed(task)
        }
    }

    companion object {
        @JvmStatic
        val BACKGROUND_TEXT_FOR_EDITH_TEXT = "Здесь отсутствует задача"
        @JvmStatic
        val TAG = "tag"
    }
}