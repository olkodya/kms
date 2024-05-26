package com.example.kms.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.example.kms.R
import com.example.kms.databinding.FragmentQRScanBinding
import com.example.kms.viewmodels.EmployeeInfoViewModel
import com.example.kms.viewmodels.authorization.AuthorizationViewModel
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class QRScanFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner

    private val CAMERA_PERMISSION_REQUEST_CODE = 10
    private val operationsViewModel by activityViewModels<OperationsViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val authorizationViewModel by activityViewModels<AuthorizationViewModel>()
    private val employeeInfoViewModel by activityViewModels<EmployeeInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel.getShift(authorizationViewModel.state.value.token?.user_id ?: 0)
        val binding = FragmentQRScanBinding.inflate(layoutInflater, container, false)
        val scannerView = binding.scannerView
        val previousFragment =
            requireParentFragment().findNavController().previousBackStackEntry?.destination?.id


        when (previousFragment) {
            R.id.employeeInfoFragment2 -> {
                binding.scannerHint.text = getString(R.string.qr_code_hint2)
            }

            else -> {
                operationsViewModel.employee.onEach {
                    if (it) {
                        binding.scannerHint.text = getString(R.string.qr_code_hint)
                    } else {
                        binding.scannerHint.text = getString(R.string.qr_code_hint2)
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }


        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)

        profileViewModel.shiftStarted.onEach {
            if (it) {
            } else {
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        lifecycleScope.launch {
            delay(500)
            if (profileViewModel.shiftStarted.value) {
                checkCameraPermission()
                codeScanner.startPreview()
                startScanning(binding)
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Ошибка. Начните смену.")
                    .setMessage("Перейдите на вкладку профиль и начните смену, для того чтобы совершить операцию.")
                    .setPositiveButton("Ок") { dialog, which -> }
                    .show()
            }
        }

        return binding.root
    }


    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }


    private fun startScanning(binding: FragmentQRScanBinding) {
        val scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            decodeCallback = DecodeCallback { result ->
                releaseResources()
                activity.runOnUiThread {

                    val previousFragment =
                        requireParentFragment().findNavController().previousBackStackEntry?.destination?.id
                    previousFragment.let {
                        when (previousFragment) {
                            R.id.employeeInfoFragment2 -> {
                                operationsViewModel.getKey(
                                    result.text,
                                    employeeInfoViewModel.employee.value.employee?.employeeId
                                )
                            }

                            else -> {
                                if (operationsViewModel.employee.value)
                                    operationsViewModel.getEmployee(result.text)
                                else {
                                    operationsViewModel.getKey(result.text, null)
                                }
                            }
                        }
                    }


                    operationsViewModel.uiState.onEach {
                        if (operationsViewModel.employee.value) {
                            if (operationsViewModel.uiState.value.isSuccessEmployee) {
                                operationsViewModel.setScanned()
                            }

                            if (operationsViewModel.uiState.value.notReturned) {
                                Toast.makeText(
                                    requireContext(),
                                    "Ключ не был возвращен, невозможно совершить операцию.",
                                    Toast.LENGTH_LONG
                                ).show()
                                operationsViewModel.reset()
                            }

                            if (operationsViewModel.uiState.value.isSuccessGiveKey && !operationsViewModel.uiState.value.hasPermission) {
                                Toast.makeText(
                                    requireContext(),
                                    "нет разрешения на ключ.",
                                    Toast.LENGTH_LONG
                                ).show()
                                operationsViewModel.reset()
                            }

                            if (operationsViewModel.uiState.value.isSuccessGiveKey && operationsViewModel.uiState.value.hasPermission) {
                                operationsViewModel.setScanned()
                            }
                        } else {
                            if (operationsViewModel.uiState.value.isSuccessTakeKey) {
                                operationsViewModel.setScanned()
                            }

                            if (operationsViewModel.uiState.value.notGivenAway) {
                                Toast.makeText(
                                    requireContext(),
                                    "Ключ не был выдан",
                                    Toast.LENGTH_LONG
                                ).show()
                                operationsViewModel.reset()

                            }
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)


                }
            }
            errorCallback = ErrorCallback {
                releaseResources()
                activity.runOnUiThread {
                }
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        // codeScanner.startPreview()
    }

}