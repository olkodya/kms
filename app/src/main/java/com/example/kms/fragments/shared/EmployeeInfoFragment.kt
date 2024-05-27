package com.example.kms.fragments.shared

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kms.R
import com.example.kms.databinding.FragmentEmployeeInfoBinding
import com.example.kms.fragments.operations.SignaturePadFragment
import com.example.kms.fragments.register.ScanKeyFragment
import com.example.kms.lists.divisions.DivisionsAdapter
import com.example.kms.lists.divisions.PermissionsAdapter
import com.example.kms.model.DTO.Division
import com.example.kms.model.DTO.Permission
import com.example.kms.model.enums.EmployeeType
import com.example.kms.network.api.EmployeeApi
import com.example.kms.network.api.ImageApi
import com.example.kms.repository.reposImpl.EmployeeRepositoryImpl
import com.example.kms.repository.reposImpl.ImageRepositoryImpl
import com.example.kms.utils.Converter.convertEmployeeType
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.example.kms.viewmodels.shared.EmployeeInfoViewModel
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
    lateinit var divAdapter: DivisionsAdapter
    lateinit var permAdapter: PermissionsAdapter


    private val viewModel by activityViewModels<EmployeeInfoViewModel> {
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
                        ScanKeyFragment.EMPLOYEE_ID to viewModel.employee.value.employee?.employeeId,
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

        initRcView(binding)

        viewModel.employee.onEach {
            if (it.employee != null) {
                binding.employeeName.text =
                    it.employee?.secondName + " " + it.employee?.firstName + " " + it.employee?.middleName
                if (it.employeeId != null) {
                    binding.certificate.text = it.employeeId.number
                } else {
                    binding.certificate.text = " "
                }
                divAdapter.submitList(it.employee.divisions)

                permAdapter.submitList(
                    getAllPermissions(
                        it.employee.permissions,
                        it.employee.divisions,
                    )
                )

                binding.position.text =
                    convertEmployeeType(it.employee.employeeType ?: EmployeeType.TEACHER)
//
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.employeePhoto.onEach {
            if (it != null && it.size != 0)
                Glide.with(requireContext()).load(it).fitCenter().transition(
                    DrawableTransitionOptions.withCrossFade()
                ).transform(RoundedCorners(32))
                    .into(binding.employeePhoto)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        // binding.employeeName.text =
        //   operationViewModel.uiState.value.employee?.first_name + " " + operationViewModel.uiState.value.employee?.second_name + " " + operationViewModel.uiState.value.employee?.middle_name
        binding.employeeToolbar


        // Inflate the layout for this fragment
        return binding.root
    }


    private fun getAllPermissions(
        permissions: List<Permission?>?,
        divisions: List<Division>?
    ): List<Permission?> {
        val list = emptyList<Permission?>().toMutableList()
        if (permissions != null) {
            for (permission in permissions) {
                list += permission
            }
        }

        if (divisions != null) {
            for (division in divisions) {
                if (division.permissions != null) {
                    for (permission in division.permissions) {
                        list += permission
                    }
                }
            }
        }
        return list
    }


    private fun initRcView(binding: FragmentEmployeeInfoBinding) {
        Log.d("SIZE", childFragmentManager.fragments.toString())
        divAdapter = DivisionsAdapter()
        binding.divisionRecyclerView.adapter = divAdapter
        permAdapter = PermissionsAdapter()
        binding.permissionRecyclerView.adapter = permAdapter


    }

}