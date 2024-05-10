package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.databinding.FragmentSignaturePadBinding
import com.example.kms.model.OperationForm
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.viewmodels.SignaturePadViewModel
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import com.github.gcacace.signaturepad.views.SignaturePad


class SignaturePadFragment : Fragment() {
    private lateinit var signaturePad: SignaturePad
    private val operationsViewModel by activityViewModels<OperationsViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val signaturePadViewModel by viewModels<SignaturePadViewModel> {
        viewModelFactory {
            initializer {
                SignaturePadViewModel(
                    OperationsRepositoryImpl(OperationApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignaturePadBinding.inflate(inflater, container, false)
        signaturePad = binding.signaturePad
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        operationsViewModel.unsetScanned()
        binding.clearBtn.setOnClickListener {
            if (!signaturePad.isEmpty) {
                signaturePad.clear()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Signature pad is already empty",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

//        binding.submitBtn.setOnClickListener {
//            if (!signaturePad.isEmpty) {
//                binding.image.setImageBitmap(signaturePad.signatureBitmap)
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "Signature pad is already empty",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
        Toast.makeText(
            requireContext(),
            operationsViewModel.uiState.value.key?.qr ?: "haha",
            Toast.LENGTH_LONG
        ).show()

        binding.finish.setOnClickListener {
            operationsViewModel.unsetScanned()
            val operationForm: OperationForm = OperationForm(
                key_id = operationsViewModel.uiState.value.key?.key_id ?: 0,
                employee_id = operationsViewModel.uiState.value.employee?.employee_id ?: 0,
                shift_id = profileViewModel.state.value.shift?.shift_id ?: 0
            )
            signaturePadViewModel.createOperation(operationForm)
            // findNavController().navigate(R.id.action_signaturePadFragment_to_bottomNavigationFragment2)
        }
        return binding.root
    }
}