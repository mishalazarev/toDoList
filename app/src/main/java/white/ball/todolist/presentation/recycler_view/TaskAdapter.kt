package white.ball.todolist.presentation.recycler_view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import white.ball.todolist.R
import white.ball.todolist.databinding.BlockTaskBinding
import white.ball.todolist.domain.model.Task
import white.ball.todolist.domain.contract.TaskActionListener


class TaskAdapter(
    private val context: Context,
    private val taskActionListener: TaskActionListener,
) : RecyclerView.Adapter<TaskAdapter.TaskHolder>(), View.OnClickListener {

    var tasksList: List<Task> = emptyList()
        set(value) {
            val taskDiffUtil = TaskDiffUtil(field, value)
            val diffResult = DiffUtil.calculateDiff(taskDiffUtil)
            field = value
            diffResult.dispatchUpdatesTo(this)
    }

    inner class TaskHolder(val binding: BlockTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BlockTaskBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreTaskImageButton.setOnClickListener(this)
        binding.isDoneTaskCheckBox.setOnClickListener(this)

        return TaskHolder(binding)
    }

    override fun getItemCount(): Int = tasksList.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = tasksList[position]

        holder.binding.apply {
            blockTaskLayout.tag = task
            nameTaskTextView.tag = task
            moreTaskImageButton.tag = task
            isDoneTaskCheckBox.tag = task

            nameTaskTextView.text = task.nameTask
            nameTaskTextView.gravity = Gravity.CENTER
            isDoneTaskCheckBox.isChecked = task.isDone

            if (task.nameTask.isEmpty()) {
                nameTaskTextView.textSize = 16f
                nameTaskTextView.hint = BACKGROUND_TEXT_FOR_EDITH_TEXT

            } else if (task.isDone && nameTaskTextView.text.isNotEmpty()) {
                val strikeTextOnHtml = "<strike>${task.nameTask}</strike>"
                nameTaskTextView.text = HtmlCompat.fromHtml(strikeTextOnHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
                nameTaskTextView.textSize = 14f
                nameTaskTextView.setTextColor(Color.GRAY)
            } else {
                nameTaskTextView.text = task.nameTask
                nameTaskTextView.textSize = 16f
                nameTaskTextView.setTextColor(Color.BLACK)
            }


        }
    }

    override fun onClick(view: View) {
        val task: Task = view.tag as Task

        when (view.id) {
            R.id.more_task_image_button -> showMenu(task, view)
            R.id.is_done_task_check_box -> taskActionListener.onUpDateTaskPressed(task)
            else -> taskActionListener.onLaunchDetailsAboutTaskPressed(task)
        }
    }

    private fun showMenu(task: Task, taskView: View) {
        val menu = PopupMenu(context, taskView)
        menu.inflate(R.menu.main_menu)
        val taskIndex = tasksList.indexOf(task)

        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.up_item -> {
                    taskActionListener.onUpTaskPressed(task)
                    true
                }
                R.id.delete_task_item -> {
                    taskActionListener.onDeleteTaskPressed(task)
                    true
                }
                R.id.down_item -> {
                    taskActionListener.onDownTaskPressed(task)
                    true
                }
                else -> {
                    throw IllegalArgumentException(context.getString(R.string.incorrect_argument_exception))
                }
            }
        }

        if (taskIndex == tasksList.size - 1) {
            menu.menu.removeItem(R.id.down_item)
        } else if (taskIndex == 0) {
            menu.menu.removeItem(R.id.up_item)
        }

        menu.show()
    }

    companion object {
        @JvmStatic
        val BACKGROUND_TEXT_FOR_EDITH_TEXT = "Здесь отсутствует задача"
        @JvmStatic
        val TAG = "tag"
    }
}