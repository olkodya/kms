package com.example.kms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.KeyForm
import com.example.kms.model.Operation
import com.example.kms.model.OperationForm
import com.example.kms.model.enums.KeyState
import com.example.kms.repository.ImageRepository
import com.example.kms.repository.KeyRepository
import com.example.kms.repository.OperationRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SignaturePadViewModel(
    private val repository: OperationRepository,
    private val keyRepository: KeyRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {
    fun createOperation(operationForm: OperationForm) {
        viewModelScope.launch {
            try {
                val operation: Operation = repository.createOperation(operationForm)
                keyRepository.updateKey(
                    operation.key.key_id ?: 0,
                    KeyForm(
                        audience_id = operation.key.audience.audience_id,
                        key_state = KeyState.GIVEN_AWAY,
                        main = operation.key.main ?: true
                    )
                )

            } catch (e: Exception) {

            }
        }
    }

    fun uploadSignature(image: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                imageRepository.upload(imageForm = image, file = file)
            } catch (e: Exception) {

            }
        }
    }
}