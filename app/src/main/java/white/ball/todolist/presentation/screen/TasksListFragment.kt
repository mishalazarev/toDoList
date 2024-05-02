package white.ball.todolist.presentation.screen

import android.nfc.Tag
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import white.ball.todolist.databinding.FragmentMainBinding
import white.ball.todolist.domain.contract.TaskActionListener
import white.ball.todolist.presentation.recycler_view.TaskAdapter
import white.ball.todolist.presentation.view_model.TasksListViewModel
import white.ball.todolist.domain.model.Task
import white.ball.todolist.presentation.dialog_screen.TaskDetailsDialogFragment
import white.ball.todolist.presentation.recycler_view.TaskSwipeGesture
import white.ball.todolist.presentation.view_model.viewModelCreator
import java.util.Collections

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

        adapter = TaskAdapter(
            requireContext(),
            object : TaskActionListener {

                override fun onAddTaskPressed() {
                    viewModel.addTask()
                }

                override fun onUpTaskPressed(task: Task) {
                    viewModel.upTask(task)
                }

                override fun onDownTaskPressed(task: Task) {
                    viewModel.downTask(task)
                }

                override fun onDeleteTaskPressed(task: Task) {
                viewModel.removeTask(task)
            }

            override fun onLaunchDetailsAboutTaskPressed(task: Task) {
                val detailTaskDialogScreen = TaskDetailsDialogFragment.newInstance(task.id)
                detailTaskDialogScreen.show(requireActivity().supportFragmentManager, detailTaskDialogScreen.tag)
            }

            override fun onUpDateTaskPressed(task: Task) {
                viewModel.upDateTask(!task.isDone, task.nameTask, task)
            }
        })

        val swipeGesture = object : TaskSwipeGesture(
            requireContext()
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition

                viewModel.tasks.value?.let {
                    Collections.swap(it, fromPosition, toPosition)
                    adapter.notifyItemMoved(fromPosition, toPosition)
                }

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        viewModel.removeTask(viewHolder.itemView.tag as Task)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.tasksList = it
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        val itemAnimator = binding.recyclerView.itemAnimator

        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshDB()
    }

    override fun onStop() {
        super.onStop()
        viewModel.refreshDB()
    }
}