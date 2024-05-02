package white.ball.todolist.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import white.ball.todolist.domain.contract.repository.TasksRepository
import java.util.*
import kotlin.collections.ArrayList

typealias TaskListener = (tasksList: List<Task>) -> Unit

class TasksService(
    private val tasksRepository: TasksRepository
) {
    private var tasksList = mutableListOf<Task>()
    private val listeners = mutableSetOf<TaskListener>()

    fun load() {
        tasksList = (getTasksList().value as MutableList<Task>?)!!
    }

    private fun getTasksList(): LiveData<List<Task>> {
        return tasksRepository.getTasks()
    }

    fun getTaskById(id: UUID): LiveData<Task> {
        val indexTask = returnIndexTask(id)
        val taskLiveData = MutableLiveData<Task>()
        taskLiveData.value = tasksList.elementAt(indexTask)
        return taskLiveData
    }

    fun createTask() {
        tasksList = ArrayList(tasksList)
        tasksList.add(Task())
        notifyChanges()
    }

    fun upTask(task: Task) {
        val taskIndex = tasksList.indexOf(task)
        tasksList = ArrayList(tasksList)
        Collections.swap(tasksList, taskIndex - 1, taskIndex)
        notifyChanges()
    }

    fun downTask(task: Task) {
        val taskIndex = tasksList.indexOf(task)
        tasksList = ArrayList(tasksList)
        Collections.swap(tasksList, taskIndex + 1, taskIndex)
        notifyChanges()
    }

    fun removeTask(task: Task): Boolean {
        val indexTask = returnIndexTask(task.id)
        tasksList = ArrayList(tasksList)
        tasksList.removeAt(indexTask)
        notifyChanges()
        return true
    }

    fun refreshTaskInDB() {
        tasksRepository.deleteAllTasks()
        tasksRepository.insertAllTasks(tasksList)
    }

    private fun returnIndexTask(id: UUID): Int {
        return tasksList.indexOfLast { it.id == id }
    }

    fun upDateTask(isDone: Boolean, newNameTask: String, oldTask: Task) {
        val indexTask = returnIndexTask(oldTask.id)
        val updatedTask = tasksList[indexTask].copy(nameTask = newNameTask, isDone = isDone)
        tasksList = ArrayList(tasksList)
        tasksList[indexTask] = updatedTask
        notifyChanges()
    }

    fun addListener(listener: TaskListener) {
        listeners.add(listener)
        listener.invoke(tasksList)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(tasksList) }
    }
}