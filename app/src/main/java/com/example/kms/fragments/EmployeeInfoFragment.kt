package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentEmployeeInfoBinding
import com.example.kms.viewmodels.operations.OperationsViewModel


class EmployeeInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEmployeeInfoBinding.inflate(inflater, container, false)
        val viewModel by activityViewModels<OperationsViewModel>()
        val navController = findNavController()
        binding.employeeToolbar.setupWithNavController(navController)

        viewModel.unsetScanned()
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(R.id.action_employeeInfoFragment2_to_scanKeyFragment2)
        }

        binding.employeeName.text = viewModel.uiState.value.employee?.first_name + " " + viewModel.uiState.value.employee?.second_name + " " + viewModel.uiState.value.employee?.middle_name
        val toolbar = binding.employeeToolbar
        // Inflate the layout for this fragment
        return binding.root
    }
}