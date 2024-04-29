package white.ball.todolist.presentation.recycler_view

import androidx.recyclerview.widget.DiffUtil
import white.ball.todolist.domain.model.Task

class TaskDiffUtil(
    private val oldTaskList: List<Task>,
    private val newTaskList: List<Task>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldTaskList.size
    override fun getNewListSize(): Int = newTaskList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentTaskInOldList = oldTaskList[oldItemPosition]
        val currentTaskInNewList = newTaskList[newItemPosition]

        return currentTaskInOldList.id == currentTaskInNewList.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentTaskInOldList = oldTaskList[oldItemPosition]
        val currentTaskInNewList = newTaskList[newItemPosition]

        return currentTaskInOldList == currentTaskInNewList
    }
}