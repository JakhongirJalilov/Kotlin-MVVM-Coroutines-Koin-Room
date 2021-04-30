package com.example.registeruser.fragments

import android.widget.Toast
import com.example.registeruser.base.RoundedBottomSheetDialogFragment
import com.example.registeruser.databinding.FragmentUserInfoBinding
import com.example.registeruser.models.UserData
import com.example.registeruser.room.AppDatabase
import com.example.registeruser.utils.gone
import com.example.registeruser.utils.visible
import com.example.registeruser.viewmodel.InfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
class UserInfoFragment :
    RoundedBottomSheetDialogFragment<FragmentUserInfoBinding>(FragmentUserInfoBinding::inflate) {
    private val infoViewModel: InfoViewModel by viewModel()

    override fun initialize() {
        binding.search.setOnClickListener {
            if (binding.username.text.toString().isNotEmpty()) {
                infoViewModel.getUser(binding.username.text.toString()).observe(this, {
                    if (it != null) {
                        binding.txUsername.text = it.username
                        binding.txFirstName.text = it.firstName
                        binding.txLastName.text = it.lastName
                        binding.txEmail.text = it.email
                        binding.txPassword.text = it.password
                        binding.txPhone.text = it.phone
                        binding.txUserStatus.text = it.userStatus.toString()

                        AppDatabase.database?.userDao()?.addUser(
                            UserData(
                                it.id,
                                it.username,
                                it.firstName,
                                it.lastName,
                                it.email,
                                it.password,
                                it.phone,
                                it.userStatus
                            )
                        )
                    }
                })
            } else {
                binding.username.error = "Please enter the field"
            }
        }
        infoViewModel.isFound.observe(this, {
            if (it) {
                Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
            }
        })

        infoViewModel.isLoading.observe(this, {
            if (it)
                binding.progress.visible()
            else
                binding.progress.gone()
        })
    }
}