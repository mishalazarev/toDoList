package white.ball.todolist.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import white.ball.todolist.domain.model.Task
import white.ball.todolist.domain.model.TasksService
import java.util.*

class TaskDetailsViewModel(
    private val tasksService: TasksService,
    idTask: UUID
) : ViewModel() {

    val currentTask = MutableLiveData<Task>()

    init {
         currentTask.value = tasksService.getTaskById(idTask).value
    }

    fun saveChangeTask(isDone: Boolean, newNameTask: String) {
         tasksService.upDateTask(isDone ,newNameTask, liveDataToTask(currentTask))
    }

    fun removeTask() {
        tasksService.removeTask(liveDataToTask(currentTask))
    }

    private fun liveDataToTask(taskLiveData :LiveData<Task>): Task {
        return Task(
            taskLiveData.value!!.id,
            taskLiveData.value!!.nameTask,
            taskLiveData.value!!.isDone
        )
    }
}