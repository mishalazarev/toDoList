package white.ball.todolist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import white.ball.todolist.domain.model.Task

@Database(
    version = 1,
    entities = [
        Task::class
    ]
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getTaskDao(): TasksDao

}