package white.ball.todolist.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import white.ball.todolist.R
import white.ball.todolist.databinding.FragmentFillTaskBinding
import white.ball.todolist.presentation.recycler_view.TaskAdapter.Companion.TAG
import white.ball.todolist.presentation.view_model.TaskDetailsViewModel
import white.ball.todolist.presentation.view_model.viewModelCreator
import java.util.*

class TaskDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFillTaskBinding
    private val viewModel: TaskDetailsViewModel by viewModelCreator{
        TaskDetailsViewModel(
            it.service,
            requireArguments().getSerializable(KEY_ID_TASK) as UUID
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFillTaskBinding.inflate(inflater, container, false)
        binding.fillTaskEditText.setText(viewModel.currentTask.value?.nameTask)

        viewModel.currentTask.value?.nameTask = binding.fillTaskEditText.text.toString()

        if (viewModel.currentTask.value?.nameTask == "") {
            binding.clearTaskImageButton.visibility = View.GONE
        } else {
            binding.clearTaskImageButton.visibility = View.VISIBLE
        }

        binding.saveChangeTaskButton.setOnClickListener{
            saveChangeTask()
        }
        binding.clearTaskImageButton.setOnClickListener{ clearTask(binding.fillTaskEditText) }
        binding.removeTaskButton.setOnClickListener {
            removeTask()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.back_image_view -> {
                requireActivity().onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveChangeTask() {
        viewModel.currentTask.value?.nameTask = binding.fillTaskEditText.text.toString()
        viewModel.saveChangeTask()
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun removeTask() {
        viewModel.removeTask()
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun clearTask(view: View) {
        val fieldForText = view as EditText
        if (fieldForText.text.toString() == EMPTY_STRING) {
            Toast.makeText(context, R.string.field_empty, Toast.LENGTH_SHORT).show()
            return
        }

        fieldForText.setText(EMPTY_STRING)
    }

    companion object {
        fun newInstance(idTask: UUID): TaskDetailsFragment {
            val args = Bundle().apply {
                putSerializable(KEY_ID_TASK,idTask)
            }
            return TaskDetailsFragment().apply {
                arguments = args
            }
        }
        @JvmStatic
        val KEY_ID_TASK = "id_task"
        @JvmStatic
        val EMPTY_STRING = ""
    }
}
