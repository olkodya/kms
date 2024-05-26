package com.example.kms.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.example.kms.model.enums.AudienceType
import com.example.kms.network.api.EmployeeApi
import com.example.kms.network.api.ImageApi
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.EmployeeRepositoryImpl
import com.example.kms.repository.ImageRepositoryImpl
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.utils.Converter.convertAudience
import com.example.kms.utils.Converter.convertDateFormat
import com.example.kms.viewmodels.register.RegisterItemViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        val binding = FragmentRegisterItemBinding.inflate(inflater, container, false)
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)

        if (arguments?.containsKey(OPERATION_ID_KEY) == true) {
            val operationId: Int = requireArguments().getInt(OPERATION_ID_KEY)
            viewModel.getOperation(operationId)
            Log.d("ID", operationId.toString())
        }
        viewModel.uiState.onEach {
            if (it.operation != null) {
                binding.giveDate.text = convertDateFormat(it.operation.give_date_time ?: "")
                if (it.operation.return_date_time != null)
                    binding.returnDate.text = convertDateFormat(it.operation.return_date_time)
                else {
                    binding.returnDate.text = "Ключ не был возвращен"
                }
                binding.audienceNumber.text =
                    it.operation?.shift?.watch?.buildingNumber.toString() + "-" + it.operation?.key?.audience?.number.toString()
                binding.audienceType.text =
                    convertAudience(it.operation.key.audience.audienceType ?: AudienceType.STUDY)
                if (viewModel.employeeId.value.id != -1)
                    binding.certificate.text = viewModel.employeeId.value.number
//
                binding.employeeName.text =
                    it.operation?.employee?.secondName + "\n" + it.operation?.employee?.firstName + "\n" + it.operation?.employee?.middleName
                viewModel.getSignatures(it.operation?.employee?.image?.image_id ?: 103)
                viewModel.getEmployeePhoto(it.operation?.employee?.image?.image_id ?: 103)
                viewModel.getAudiencePhoto(it.operation?.employee?.image?.image_id ?: 103)
                viewModel.getEmployeeID(it.operation.employee?.image?.image_id ?: 103)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.employeePhoto.collect {
                if (it != null) {
                    Glide.with(requireContext()).load(it).fitCenter().circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.avatar)
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.audiencePhoto.collect {
                if (it?.size != 0) {
                    Glide.with(requireContext()).load(it).fitCenter().circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.avatarAudience)
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.giveSignature.collect {
                if (it?.size != 0) {
                    Glide.with(requireContext()).load(it).transition(
                        DrawableTransitionOptions.withCrossFade()
                    ).fitCenter()
                        .into(binding.giveSignature)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.returnSignature.collect {
                if (it != null && it.isNotEmpty()) {
                    binding.returnCard.visibility = View.VISIBLE
                    Glide.with(requireContext()).load(it).transition(
                        DrawableTransitionOptions.withCrossFade()
                    ).fitCenter()
                        .into(binding.returnSignature)
                }
            }
        }





        binding.giveSignature.setOnClickListener {
            val imageView = ImageView(requireContext())
            val bmp = BitmapFactory.decodeByteArray(
                viewModel.giveSignature.value,
                0,
                viewModel.giveSignature.value?.size ?: 0
            )
            imageView.setImageBitmap(bmp)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Подпись сотрудника")
                .setView(imageView)
                .setPositiveButton("Ок") { dialog, which ->

                }
                .show()
        }

        binding.returnSignature.setOnClickListener {
            val imageView = ImageView(requireContext())
            val bmp = BitmapFactory.decodeByteArray(
                viewModel.returnSignature.value,
                0,
                viewModel.returnSignature.value?.size ?: 0
            )
            imageView.setImageBitmap(bmp)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Подпись сотрудника")
                .setView(imageView)
                .setPositiveButton("Ок") { dialog, which ->

                }
                .show()
        }

        binding.employeeCard.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerItemFragment_to_employeeInfoFragment2,
                bundleOf(
                    EMPLOYEE_ID to viewModel.uiState.value.operation?.employee?.employeeId,
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