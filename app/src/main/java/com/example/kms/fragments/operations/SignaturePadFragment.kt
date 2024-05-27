package com.example.kms.fragments.operations

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentSignaturePadBinding
import com.example.kms.fragments.shared.EmployeeInfoFragment
import com.example.kms.model.DTO.OperationForm
import com.example.kms.network.api.AudienceApi
import com.example.kms.network.api.ImageApi
import com.example.kms.network.api.KeyApi
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.reposImpl.AudienceRepositoryImpl
import com.example.kms.repository.reposImpl.ImageRepositoryImpl
import com.example.kms.repository.reposImpl.KeyRepositoryImpl
import com.example.kms.repository.reposImpl.OperationsRepositoryImpl
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.example.kms.viewmodels.operations.SignaturePadViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class SignaturePadFragment : Fragment() {
    companion object {
        const val EMPLOYEE_ID = "EMPLOYEE_ID"
        const val KEY_ID = "KEY_ID"
        const val OPERATION = "OPERATION"
        const val OPERATION_ID = "OPERATION_ID"
    }

    var employeeId: Int = 0
    var keyId: Int = 0
    var operationId: Int = 0

    private lateinit var signaturePad: SignaturePad
    private val operationsViewModel by activityViewModels<OperationsViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val signaturePadViewModel by viewModels<SignaturePadViewModel> {
        viewModelFactory {
            initializer {
                SignaturePadViewModel(
                    OperationsRepositoryImpl(OperationApi.INSTANCE),
                    KeyRepositoryImpl(KeyApi.INSTANCE),
                    ImageRepositoryImpl(ImageApi.INSTANCE),
                    AudienceRepositoryImpl(AudienceApi.INSTANCE),
                )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.containsKey(EMPLOYEE_ID) == true) {
            val employee: Int = requireArguments().getInt(EMPLOYEE_ID)
            employeeId = employee
            Log.d("scan", employeeId.toString())
        }

        if (arguments?.containsKey(KEY_ID) == true) {
            keyId = requireArguments().getInt(KEY_ID)
            Log.d("key", keyId.toString())
        }

        if (arguments?.containsKey(EmployeeInfoFragment.OPERATION_ID) == true) {
            val operation: Int = requireArguments().getInt(EmployeeInfoFragment.OPERATION_ID)
            operationId = operation
            Log.d("scan", operationId.toString())
        }

        Log.d("ID", employeeId.toString())
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
        Toast.makeText(
            requireContext(),
            operationsViewModel.uiState.value.key?.qr ?: "haha",
            Toast.LENGTH_LONG
        ).show()

        binding.finish.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Вы уверены, что хотите выдать ключ сотруднику?")
                .setNegativeButton("Отмена") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Завершить") { dialog, which ->
                    val operationForm = OperationForm(
                        key_id = keyId,
                        employee_id = employeeId,
                        shift_id = profileViewModel.state.value.shift?.shift_id ?: 0
                    )
                    if (operationsViewModel.employee.value) {
                        signaturePadViewModel.createOperation(operationForm)
                        createSignature(true)
                    } else {
                        signaturePadViewModel.finishOperation(operationId)
                        createSignature(false)
                    }
                    signaturePadViewModel.signature.onEach {
                        operationsViewModel.reset()
                        if (it.image_id != -1)
                            findNavController().navigate(R.id.action_signaturePadFragment_to_bottomNavigationFragment2)
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                }.show()

        }
        return binding.root
    }


    private fun createSignature(giveOrReturn: Boolean) {
        signaturePadViewModel.uiState.onEach {
            var filename = ""
            if (it.operation != null) {
                filename = if (giveOrReturn) {
                    "signature_give_" + it.operation.operation_id
                } else {
                    "signature_take_" + it.operation.operation_id
                }

            }
            val f = bitmapToFile(signaturePad.signatureBitmap, filename)
            val reqFile: RequestBody =
                f.asRequestBody("image/*".toMediaTypeOrNull())
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", f.name, reqFile)
            val fullName: RequestBody =
                "Your Name".toRequestBody("multipart/form-data".toMediaTypeOrNull())
            signaturePadViewModel.uploadSignature(
                fullName,
                body,
                it.operation?.operation_id ?: 0,
                giveOrReturn
            )
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File {
        val file = File(context?.cacheDir, fileNameToSave)
        return try {
            file.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file
        }
    }
}