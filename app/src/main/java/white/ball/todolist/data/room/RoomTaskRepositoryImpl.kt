package white.ball.todolist.data.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import white.ball.todolist.domain.model.Task
import white.ball.todolist.domain.contract.repository.TasksRepository

class RoomTaskRepositoryImpl(
    private val tasksDao: TasksDao,
) : TasksRepository {

    override fun getTasks(): LiveData<List<Task>> {
        val taskLiveDataList = MutableLiveData<List<Task>>()
        taskLiveDataList.value = tasksDao.getAllTasks()
        return taskLiveDataList
    }

    override fun deleteAllTasks() {
        tasksDao.deleteAllTasks()
    }

    override fun insertAllTasks(taskList: List<Task>) {
        tasksDao.insertAllTasks(taskList)
    }
}