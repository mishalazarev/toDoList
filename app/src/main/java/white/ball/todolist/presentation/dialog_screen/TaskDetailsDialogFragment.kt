package white.ball.todolist.presentation.dialog_screen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import white.ball.todolist.R
import white.ball.todolist.databinding.DialogFragmentFillTaskBinding
import white.ball.todolist.presentation.view_model.TaskDetailsViewModel
import white.ball.todolist.presentation.view_model.viewModelCreator
import java.util.UUID

class TaskDetailsDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentFillTaskBinding
    private lateinit var emptyField: Snackbar

    private val viewModel: TaskDetailsViewModel by viewModelCreator{
        TaskDetailsViewModel(
            it.service,
            requireArguments().getSerializable(KEY_ID_TASK) as UUID
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(requireContext(), R.layout.dialog_fragment_fill_task, null)
        binding = DialogFragmentFillTaskBinding.bind(view)
        val dialog = AlertDialog.Builder(requireContext())

        with(binding) {
            fillTaskEditText.setText(viewModel.currentTask.value?.nameTask)

            closeDialogScreenImageButton.setOnClickListener {
                dismiss()
            }

            saveChangeTaskButton.setOnClickListener {
                saveChangeTask()
            }

            clearTaskButton.setOnClickListener {
                clearTask(fillTaskEditText)
            }

//            removeTaskButton.setOnClickListener {
//                removeTask()
//            }
        }

        return dialog
            .setView(view)
            .create()
    }

    override fun onStart() {
        super.onStart()

        emptyField = Snackbar.make(binding.root, R.string.empty_field, Snackbar.LENGTH_SHORT)
        emptyField.setTextColor(requireContext().getColor(R.color.white))
        emptyField.setBackgroundTint(requireContext().getColor(R.color.gray))
    }

    private fun saveChangeTask() {
        viewModel.saveChangeTask(viewModel.currentTask.value!!.isDone ,binding.fillTaskEditText.text.toString())
        dismiss()
    }

    private fun removeTask() {
        viewModel.removeTask()
        dismiss()
    }

    private fun clearTask(view: View) {
        val fieldForText = view as EditText
        if (fieldForText.text.toString() == EMPTY_STRING && !emptyField.isShown) {
            emptyField.show()
            return
        }

        fieldForText.setText(EMPTY_STRING)
    }

    companion object {
        fun newInstance(idTask: UUID): TaskDetailsDialogFragment {
            val args = Bundle().apply {
                putSerializable(KEY_ID_TASK,idTask)
            }
            return TaskDetailsDialogFragment().apply {
                arguments = args
            }
        }

        @JvmStatic
        val KEY_ID_TASK = "id_task"
        @JvmStatic
        val EMPTY_STRING = ""
    }
}