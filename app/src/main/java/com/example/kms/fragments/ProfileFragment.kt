package com.example.kms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kms.R
import com.example.kms.databinding.FragmentProfileBinding
import com.example.kms.model.Watch
import com.example.kms.network.api.ShiftApi
import com.example.kms.network.api.WatchApi
import com.example.kms.repository.ShiftRepositoryImpl
import com.example.kms.repository.WatchRepositoryImpl
import com.example.kms.viewmodels.authorization.AuthorizationViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileFragment : Fragment() {
    val viewModel by activityViewModels<AuthorizationViewModel> ()
    val profileViewModel by activityViewModels<ProfileViewModel> {
        viewModelFactory {
            initializer {
                ProfileViewModel(
                    ShiftRepositoryImpl(ShiftApi.INSTANCE),
                    WatchRepositoryImpl(WatchApi.INSTANCE),
                )
            }
        }
    }



    override fun onStart() {
        super.onStart()
        profileViewModel.getShift(viewModel.state.value.token?.user_id?:0)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       val binding = FragmentProfileBinding.inflate(inflater, container, false)
       val employee = viewModel.state.value.token?.employee



        binding.loginName.text = viewModel.state.value.token?.username
        binding.name.text = "${employee?.first_name} ${employee?.second_name} ${employee?.middle_name}"

        val checkedItem = 0
        var singleItems = arrayOf("jhshdsj")

        profileViewModel.state.onEach { state->
                if(state.watches.isNotEmpty()) {
//                    singleItems = arrayOf(it.watches[0].building_number.toString())
                        singleItems = state.watches.map { it.building_number.toString()}.toTypedArray()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)




        binding.startShift.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Выбрать вахту")
                .setNegativeButton("Отмена") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Начать смену") { dialog, which ->
                    viewModel.state.value.token?.user_id?.let { it1 -> profileViewModel.startShift(it1, singleItems[checkedItem].toInt()) }
                }
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    // Respond to item chosen
                }
                .show()

        }

        binding.finishShift.setOnClickListener {
            viewModel.state.value.token?.user_id?.let { it1 -> profileViewModel.finishShift() }
        }


        profileViewModel.shiftStarted.onEach {
            if (!it) {
                binding.startShift.visibility = View.VISIBLE
                binding.finishShift.visibility = View.GONE
                binding.watch.visibility = View.GONE
                binding.shift.visibility = View.GONE
                binding.shiftDate.visibility = View.GONE
                binding.watchNumber.visibility = View.GONE
            } else {
                binding.startShift.visibility = View.GONE
                binding.finishShift.visibility = View.VISIBLE
                binding.watch.visibility = View.VISIBLE
                binding.shift.visibility = View.VISIBLE
                binding.shiftDate.visibility = View.VISIBLE
                binding.watchNumber.visibility = View.VISIBLE
                binding.watchNumber.text = profileViewModel.state.value.shift?.watch?.building_number.toString()
                binding.shiftDate.text = profileViewModel.state.value.shift?.start_date_time
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }
}