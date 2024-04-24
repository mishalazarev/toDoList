package white.ball.todolist.presentation.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import white.ball.todolist.R
import white.ball.todolist.databinding.FragmentMainBinding
import white.ball.todolist.domain.contract.TaskActionListener
import white.ball.todolist.presentation.recycler_view.TaskAdapter
import white.ball.todolist.presentation.view_model.TasksListViewModel
import white.ball.todolist.domain.model.Task
import white.ball.todolist.presentation.recycler_view.TaskAdapter.Companion.TAG
import white.ball.todolist.presentation.view_model.viewModelCreator

class TasksListFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: TaskAdapter

    private val viewModel: TasksListViewModel by viewModelCreator {
        TasksListViewModel(it.service)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        val layoutManager = LinearLayoutManager(requireContext())

        binding.addTaskImageButton.setOnClickListener {
            viewModel.addTask()
        }

        adapter = TaskAdapter(object : TaskActionListener {
            override fun onAddTaskPressed() {
                viewModel.addTask()
            }

            override fun onRemoveTaskPressed(task: Task) {
                viewModel.removeTask(task)
            }

            override fun onLaunchDetailsAboutTaskPressed(task: Task) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container_view, TaskDetailsFragment.newInstance(task.id))
                    .commit()
            }

            override fun onUpDateTaskPressed(task: Task) {
                task.isDone = !task.isDone
                viewModel.upDateTask(task)
            }
        })

        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.tasksList = it
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.refreshDB()
    }
}