package white.ball.todolist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import white.ball.todolist.domain.model.Task

@Dao
interface TasksDao {

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<Task>

    @Query("DELETE FROM task")
    fun deleteAllTasks()

    @Insert
    fun insertAllTasks(taskList: List<Task>)
}