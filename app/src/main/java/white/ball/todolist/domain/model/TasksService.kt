package white.ball.todolist.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import white.ball.todolist.domain.contract.repository.TasksRepository
import java.util.*
import java.util.concurrent.Executors

typealias TaskListener = (tasksList: List<Task>) -> Unit

class TasksService(
    private val tasksRepository: TasksRepository
) {
    private var tasksList = mutableListOf<Task>()
    private val listeners = mutableSetOf<TaskListener>()

    fun load() {
        tasksList = (getTasksList().value as MutableList<Task>?)!!
    }

    fun getTasksList(): LiveData<List<Task>> {
        return tasksRepository.getTasks()
    }

    fun getTaskById(id: UUID): LiveData<Task> {
        val indexTask = returnIndexTask(id)
        val taskLiveData = MutableLiveData<Task>()
        taskLiveData.value = tasksList.elementAt(indexTask)
        return taskLiveData
    }

    fun createTask() {
        tasksList.add(Task())
        notifyChanges()
    }

    fun removeTask(task: Task): Boolean {
        val indexTask = returnIndexTask(task.id)
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


    fun upDateTask(task: Task) {
        val indexTask = returnIndexTask(task.id)
        tasksList[indexTask] = task
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