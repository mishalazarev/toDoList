package white.ball.todolist.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import white.ball.todolist.domain.model.Task
import white.ball.todolist.domain.model.TaskListener
import white.ball.todolist.domain.model.TasksService

class TasksListViewModel(
    private val taskService: TasksService
) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val taskListener: TaskListener = {
        _tasks.value = it
    }

    init {
        taskService.load()
        taskService.addListener(taskListener)
    }

    fun addTask() {
        taskService.createTask()
    }

    fun removeTask(task: Task) {
        taskService.removeTask(task)
    }

    fun upDateTask(task: Task) {
        taskService.upDateTask(task)
    }

    fun refreshDB() {
        taskService.refreshTaskInDB()
    }
}