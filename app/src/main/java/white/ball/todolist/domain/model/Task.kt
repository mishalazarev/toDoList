package white.ball.todolist.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(
    tableName = "task"
)
data class Task(
    @ColumnInfo(name = "id") @PrimaryKey var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name_task") var nameTask: String = "",
    @ColumnInfo(name = "is_done") var isDone: Boolean = false
)
