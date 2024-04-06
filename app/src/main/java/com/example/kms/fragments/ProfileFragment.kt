package com.example.kms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentOperationsBinding
import com.example.kms.databinding.FragmentProfileBinding
import com.example.kms.model.Employee
import com.example.kms.network.api.ShiftApi
import com.example.kms.network.api.UserApi
import com.example.kms.repository.NetworkUserRepository
import com.example.kms.repository.ShiftRepositoryImpl
import com.example.kms.viewmodels.authorization.AuthorizationViewModel
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel by activityViewModels<AuthorizationViewModel> ()
        val profileViewModel by activityViewModels<ProfileViewModel> {
            viewModelFactory {
                initializer {
                    ProfileViewModel(
                        ShiftRepositoryImpl(ShiftApi.INSTANCE)
                    )
                }
            }
        }
       val binding = FragmentProfileBinding.inflate(inflater, container, false)
       val employee = viewModel.state.value.token?.employee


        profileViewModel.getShift(viewModel.state.value.token?.user_id?:0)

        binding.loginName.text = viewModel.state.value.token?.username
        binding.name.text = "${employee?.first_name} ${employee?.second_name} ${employee?.middle_name}"
        binding.startShift.setOnClickListener {
            viewModel.state.value.token?.user_id?.let { it1 -> profileViewModel.startShift(it1, 1) }
        }

        binding.finishShift.setOnClickListener {
            profileViewModel.finishShift(4)
        }


        profileViewModel.shiftStarted.onEach {
            if (!it) {
                binding.startShift.visibility = View.VISIBLE
                binding.finishShift.visibility = View.GONE
                binding.watch.visibility = View.GONE
                binding.shift.visibility = View.GONE

            } else {
                binding.startShift.visibility = View.GONE
                binding.finishShift.visibility = View.VISIBLE
                binding.watch.visibility = View.VISIBLE
                binding.shift.visibility = View.VISIBLE
                binding.watchNumber.text = profileViewModel.state.value.shift?.watch?.building_number.toString()
                binding.shiftDate.text = profileViewModel.state.value.shift?.start_date_time
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }
}