package white.ball.todolist.domain.contract

import white.ball.todolist.domain.model.Task

interface TaskActionListener {

    fun onAddTaskPressed()

    fun onUpTaskPressed(task: Task)

    fun onDownTaskPressed(task: Task)

    fun onDeleteTaskPressed(task: Task)

    fun onLaunchDetailsAboutTaskPressed(task: Task)

    fun onUpDateTaskPressed(task: Task)

}