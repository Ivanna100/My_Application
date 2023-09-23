package com.ispolska.myApplication.task_2.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.task_2.databinding.ActivityAddUserBinding
import com.ispolska.myApplication.task_2.domain.model.User
import com.ispolska.myApplication.task_2.ui.activities.UserViewModel
import com.ispolska.myApplication.task_2.utils.Constants

class DialogFragment : AppCompatDialogFragment() {

    private lateinit var binding: ActivityAddUserBinding

    private var userViewModel = UserViewModel()
    fun setViewModel(userViewModel: UserViewModel) {
        this.userViewModel = userViewModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        builder.setView(binding.root)
            .setPositiveButton(Constants.DIALOG_POSITIVE_BUTTON) { _, _ ->
                userViewModel.addUser(
                    User(
                        binding.textInputEditTextFullName.text.toString(),
                        binding.textInputEditTextCareer.text.toString(),
                        ""
                    ), userViewModel.getUserList().size
                )
            }.setNegativeButton(Constants.DIALOG_NEGATIVE_BUTTON) { _, _ ->
                dismiss()
            }
        return builder.create()
    }
}