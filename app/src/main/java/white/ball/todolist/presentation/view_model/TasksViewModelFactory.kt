package white.ball.todolist.presentation.view_model

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import white.ball.todolist.domain.base.App

typealias ViewModelCreator = (App) -> ViewModel?

class TasksViewModelFactory(
    private val app: App,
    private val viewModelCreator: ViewModelCreator = { null }
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelCreator(app) as T
    }
}

inline fun<reified VM: ViewModel> Fragment.viewModelCreator(noinline creator: ViewModelCreator): Lazy<VM> {
    return viewModels { TasksViewModelFactory(requireContext().applicationContext as App,creator) }
}
