package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentEmployeeInfoBinding
import com.example.kms.network.api.EmployeeApi
import com.example.kms.repository.EmployeeRepositoryImpl
import com.example.kms.viewmodels.EmployeeInfoViewModel
import com.example.kms.viewmodels.operations.OperationsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class EmployeeInfoFragment : Fragment() {

    companion object {
        const val EMPLOYEE_ID = "EMPLOYEE_ID"
    }

    private val viewModel by viewModels<EmployeeInfoViewModel> {
        viewModelFactory {
            initializer {
                EmployeeInfoViewModel(
                    EmployeeRepositoryImpl(EmployeeApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEmployeeInfoBinding.inflate(inflater, container, false)
        val operationViewModel by activityViewModels<OperationsViewModel>()
        val navController = findNavController()
        binding.employeeToolbar.setupWithNavController(navController)

        operationViewModel.unsetScanned()
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(R.id.action_employeeInfoFragment2_to_scanKeyFragment2)
        }
        if (arguments?.containsKey(EMPLOYEE_ID) == true) {
            val employeeId: Int = requireArguments().getInt(EMPLOYEE_ID)
            viewModel.getByID(employeeId)
            Log.d("ID", employeeId.toString())
        }

        viewModel.employee.onEach {

            binding.employeeName.text =
                it.employee?.second_name + " " + it.employee?.first_name + " " + it.employee?.middle_name

            binding.division.text = it.employee?.employee_type

        }.launchIn(viewLifecycleOwner.lifecycleScope)
        // binding.employeeName.text =
        //   operationViewModel.uiState.value.employee?.first_name + " " + operationViewModel.uiState.value.employee?.second_name + " " + operationViewModel.uiState.value.employee?.middle_name
        binding.employeeToolbar
        // Inflate the layout for this fragment
        return binding.root
    }
}