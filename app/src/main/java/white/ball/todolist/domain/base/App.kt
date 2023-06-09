package white.ball.todolist.domain.base

import android.app.Application
import androidx.room.Room
import white.ball.todolist.data.room.AppDataBase
import white.ball.todolist.data.room.RoomTaskRepositoryImpl
import white.ball.todolist.domain.model.TasksService
import white.ball.todolist.domain.contract.repository.TasksRepository

class App : Application() {

    private lateinit var dataBase: AppDataBase
    private lateinit var taskRepository: TasksRepository
    lateinit var service: TasksService

    override fun onCreate() {
        super.onCreate()
        dataBase = Room
            .databaseBuilder(this, AppDataBase::class.java, "database_task.db")
            .allowMainThreadQueries()
            .build()

        taskRepository = RoomTaskRepositoryImpl(dataBase.getTaskDao())

        service = TasksService(taskRepository)
    }
}