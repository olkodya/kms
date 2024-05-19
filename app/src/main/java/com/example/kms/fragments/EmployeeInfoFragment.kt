package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kms.R
import com.example.kms.databinding.FragmentEmployeeInfoBinding
import com.example.kms.model.Division
import com.example.kms.model.Permission
import com.example.kms.network.api.EmployeeApi
import com.example.kms.network.api.ImageApi
import com.example.kms.repository.EmployeeRepositoryImpl
import com.example.kms.repository.ImageRepositoryImpl
import com.example.kms.viewmodels.EmployeeInfoViewModel
import com.example.kms.viewmodels.operations.OperationsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class EmployeeInfoFragment : Fragment() {

    companion object {
        const val EMPLOYEE_ID = "EMPLOYEE_ID"
        const val EMPLOYEE_IMAGE_ID = "EMPLOYEE_IMAGE_ID"
        const val OPERATION = "OPERATION"
        const val OPERATION_ID = "OPERATION_ID"
    }

    var operationId: Int = 0

    private val viewModel by viewModels<EmployeeInfoViewModel> {
        viewModelFactory {
            initializer {
                EmployeeInfoViewModel(
                    EmployeeRepositoryImpl(EmployeeApi.INSTANCE),

                    ImageRepositoryImpl(ImageApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.containsKey(OPERATION_ID) == true) {
            val operation: Int = requireArguments().getInt(OPERATION_ID)
            operationId = operation
            Log.d("scan", operationId.toString())
        }
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
            if (operationViewModel.employee.value) {
                findNavController().navigate(
                    R.id.action_employeeInfoFragment2_to_scanKeyFragment2,
                    bundleOf(
                        ScanKeyFragment.EMPLOYEE_ID to viewModel.employee.value.employee?.employee_id,
                        ScanKeyFragment.OPERATION to true
                    )
                )
            } else {
                findNavController().navigate(
                    R.id.action_employeeInfoFragment2_to_signaturePadFragment,
                    bundleOf(
                        ScanKeyFragment.OPERATION to true,
                        SignaturePadFragment.OPERATION_ID to operationId
                    )
                )
            }
        }
        if (arguments?.containsKey(EMPLOYEE_ID) == true) {
            val employeeId: Int = requireArguments().getInt(EMPLOYEE_ID)
            viewModel.getByID(employeeId)
            viewModel.getEmployeeIDById(employeeId)
            Log.d("ID", employeeId.toString())
        }

        if (arguments?.containsKey(EMPLOYEE_IMAGE_ID) == true) {
            val imageId: Int = requireArguments().getInt(EMPLOYEE_IMAGE_ID)
            viewModel.getEmployeePhoto(imageId)
        }

        if (arguments?.containsKey(OPERATION) == true) {
            binding.continueBtn.visibility = View.VISIBLE
        }

        operationViewModel.reset()
        viewModel.employee.onEach {
            if (it.employee != null) {
                binding.employeeName.text =
                    it.employee?.second_name + " " + it.employee?.first_name + " " + it.employee?.middle_name
                binding.certificate.text = it.employeeId?.number.toString()
                binding.division.text = getStringDivisions(it.employee?.divisions)
                binding.position.text = it.employee?.employee_type
                binding.permissions.text = getStringPermissions(it.employee?.permissions)
                if (it.employeePhoto != null)
                    Glide.with(requireContext()).load(it.employeePhoto).fitCenter().transition(
                        DrawableTransitionOptions.withCrossFade()
                    )
                        .into(binding.employeePhoto)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        // binding.employeeName.text =
        //   operationViewModel.uiState.value.employee?.first_name + " " + operationViewModel.uiState.value.employee?.second_name + " " + operationViewModel.uiState.value.employee?.middle_name
        binding.employeeToolbar
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getStringDivisions(divisions: List<Division?>?): String {
        var string = ""
        if (divisions != null) {
            for (division in divisions) {
                string += division?.name + " "
            }
        }
        return string
    }

    private fun getStringPermissions(permissions: List<Permission?>?): String {
        var string = ""
        if (permissions != null) {
            for (permission in permissions) {
                string += permission?.name + " "
            }
        }
        return string
    }

}