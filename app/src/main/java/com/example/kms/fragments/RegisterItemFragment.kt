package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kms.R
import com.example.kms.databinding.FragmentRegisterItemBinding
import com.example.kms.fragments.EmployeeInfoFragment.Companion.EMPLOYEE_ID
import com.example.kms.fragments.EmployeeInfoFragment.Companion.EMPLOYEE_IMAGE_ID
import com.example.kms.network.api.ImageApi
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.ImageRepositoryImpl
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.viewmodels.register.RegisterItemViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class RegisterItemFragment : Fragment() {

    companion object {
        const val OPERATION_ID_KEY = "CHECK_ID_KEY"
    }

    private val viewModel by viewModels<RegisterItemViewModel> {
        viewModelFactory {
            initializer {
                RegisterItemViewModel(
                    OperationsRepositoryImpl(OperationApi.INSTANCE),
                    ImageRepositoryImpl(ImageApi.INSTANCE),
                )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.containsKey(OPERATION_ID_KEY) == true) {
            val operationId: Int = requireArguments().getInt(OPERATION_ID_KEY)
            viewModel.getOperation(operationId)
            Log.d("ID", operationId.toString())
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterItemBinding.inflate(inflater, container, false)
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        viewModel.uiState.onEach {
            if (it.operation != null) {
                binding.giveDate.text = it.operation?.give_date_time
                if (it.operation.return_date_time != null)
                    binding.returnDate.text = it.operation.return_date_time
                else {
                    binding.returnDate.text = "Ключ не был возвращен"
                }
                binding.audienceNumber.text =
                    it.operation?.shift?.watch?.building_number.toString() + "-" + it.operation?.key?.audience?.number.toString()
                binding.audienceType.text = it.operation?.key?.audience?.audienceType.toString()
                binding.certificate.text = "sssd"
                binding.employeeName.text =
                    it.operation?.employee?.second_name + "\n" + it.operation?.employee?.first_name + "\n" + it.operation?.employee?.middle_name
//                viewModel.getEmployeePhoto(
//                    viewModel.uiState.value.operation?.employee?.image?.image_id ?: 1
//                )
            }

            if (it.employeePhoto != null) {
//                val decodedBytes: ByteArray = it.employeePhoto
//                val decodedBitmap: Bitmap =
//                    BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size ?: 1)
//                binding.avatar.setImageBitmap(decodedBitmap)

            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {

                if (it.employeePhoto != null) {
                    Glide.with(requireContext()).load(it.employeePhoto).fitCenter().circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.avatar)
                }

                if (it.audiencePhoto != null) {
                    Glide.with(requireContext()).load(it.audiencePhoto).transition(
                        DrawableTransitionOptions.withCrossFade()
                    ).fitCenter().circleCrop()
                        .into(binding.avatarAudience)
                }
            }

        }
//        binding.giveDate.text = viewModel.uiState.value.operation?.give_date_time
//        binding.returnDate.text = viewModel.uiState.value.operation?.return_date_time
        binding.employeeCard.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerItemFragment_to_employeeInfoFragment2,
                bundleOf(
                    EMPLOYEE_ID to viewModel.uiState.value.operation?.employee?.employee_id,
                    EMPLOYEE_IMAGE_ID to viewModel.uiState.value.operation?.employee?.image?.image_id
                )
            )
        }

        binding.audienceCard.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerItemFragment_to_audienceInfoFragment,
                bundleOf(
                    AudienceInfoFragment.AUDIENCE_ID to viewModel.uiState.value.operation?.key?.audience?.audience_id,
                    AudienceInfoFragment.AUDIENCE_IMAGE_ID to viewModel.uiState.value.operation?.key?.audience?.image?.image_id
                )
            )
        }
        return binding.root
    }
}