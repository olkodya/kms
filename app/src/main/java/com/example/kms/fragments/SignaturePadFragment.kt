package com.example.kms.fragments

import android.graphics.Bitmap
import android.os.Bundle
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
import com.example.kms.databinding.FragmentSignaturePadBinding
import com.example.kms.model.OperationForm
import com.example.kms.network.api.ImageApi
import com.example.kms.network.api.KeyApi
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.ImageRepositoryImpl
import com.example.kms.repository.KeyRepositoryImpl
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.viewmodels.SignaturePadViewModel
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import com.github.gcacace.signaturepad.views.SignaturePad
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class SignaturePadFragment : Fragment() {
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
            val operationForm: OperationForm = OperationForm(
                key_id = operationsViewModel.uiState.value.key?.key_id ?: 0,
                employee_id = operationsViewModel.uiState.value.employee?.employee_id ?: 0,
                shift_id = profileViewModel.state.value.shift?.shift_id ?: 0
            )
//            signaturePadViewModel.createOperation(operationForm)

            //operationsViewModel.reset()

            viewLifecycleOwner.lifecycleScope.launch {
                val filename = "haha.png"
                val f = bitmapToFile(signaturePad.signatureBitmap, filename)
//                val f = File(context?.cacheDir, filename)
//                f.createNewFile()
//
//                // Convert bitmap to byte array
//                val bitmap: Bitmap = signaturePad.signatureBitmap
//                val bos = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, bos)
//                val bitmapData: ByteArray = bos.toByteArray()
//                // Write the bytes in file
//                val fos: FileOutputStream?
//                try {
//                    fos = FileOutputStream(f)
//                    fos.write(bitmapData)
//                    fos.flush()
//                    fos.close()
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
                val reqFile: RequestBody = f.asRequestBody("image/*".toMediaTypeOrNull())
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("image", f.name, reqFile)
                val fullName: RequestBody =
                    "Your Name".toRequestBody("multipart/form-data".toMediaTypeOrNull())

                signaturePadViewModel.uploadSignature(fullName, body)
            }
            //findNavController().navigate(R.id.action_signaturePadFragment_to_bottomNavigationFragment2)
        }
        return binding.root
    }


    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File { // File name like "image.png"
        //create a file to write bitmap data
        // var file: File
        // file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
        val file = File(context?.cacheDir, fileNameToSave)
        return try {
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file
            // it will return null
        }
    }
}