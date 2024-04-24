package white.ball.todolist.domain.contract.repository

import androidx.lifecycle.LiveData
import white.ball.todolist.domain.model.Task

interface TasksRepository {

    fun getTasks(): LiveData<List<Task>>

    fun deleteAllTasks()

    fun insertAllTasks(taskList: List<Task>)

}