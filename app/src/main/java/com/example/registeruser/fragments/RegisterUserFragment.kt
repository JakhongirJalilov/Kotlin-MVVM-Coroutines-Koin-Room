package com.example.registeruser.fragments

import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.registeruser.base.RoundedBottomSheetDialogFragment
import com.example.registeruser.databinding.FragmentRegisterUserBinding
import com.example.registeruser.models.UserData
import com.example.registeruser.utils.gone
import com.example.registeruser.utils.isValidForEmail
import com.example.registeruser.utils.visible
import com.example.registeruser.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
class RegisterUserFragment :
    RoundedBottomSheetDialogFragment<FragmentRegisterUserBinding>(FragmentRegisterUserBinding::inflate) {
    private val registerViewModel: RegisterViewModel by viewModel()
    private var userStatus = 0

    override fun initialize() {
        (binding.group.getChildAt(0) as RadioButton).isChecked = true

        binding.btRegister.setOnClickListener {
            setUserStatus(binding.group)
            if (checkFields()) {
                registerViewModel.registerUser(
                    UserData(
                        0,
                        binding.username.text.toString(),
                        binding.firstName.text.toString(),
                        binding.lastName.text.toString(),
                        binding.email.text.toString(),
                        binding.password.text.toString(),
                        binding.phone.text.toString(),
                        userStatus
                    )
                ).observe(this, {
                    if (it) {
                        Toast.makeText(
                            requireContext(),
                            "User successfully created!",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismiss()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong, please try again later!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }

        registerViewModel.isLoading.observe(this, {
            if (it)
                binding.progress.visible()
            else
                binding.progress.gone()
        })
    }

    private fun checkFields(): Boolean {

        if (binding.username.text.toString().isEmpty()) {
            binding.username.requestFocus()
            binding.username.error = ("Field is required.")
            return false
        }

        if (binding.firstName.text.toString().isEmpty()) {
            binding.firstName.requestFocus()
            binding.firstName.error = ("Field is required.")
            return false
        }

        if (binding.lastName.text.toString().isEmpty()) {
            binding.lastName.requestFocus()
            binding.lastName.error = ("Field is required.")
            return false
        }

        if (!binding.email.isValidForEmail()) {
            binding.email.requestFocus()
            binding.email.error = ("Email should be like example@company.com")
            return false
        }

        if (binding.password.text.toString().isEmpty()) {
            binding.password.requestFocus()
            binding.password.error = ("Field is required.")
            return false
        }

        if (binding.phone.text.toString().length < 11) {
            binding.phone.requestFocus()
            binding.phone.error =
                ("Phone number should be at least 11 characters with Country Code.")
            return false
        }

        return true
    }

    private fun setUserStatus(group: ViewGroup) {
        for (i in 0 until group.childCount) {
            if ((group.getChildAt(i) as RadioButton).isChecked) {
                userStatus = i + 1
            }
        }
    }
}