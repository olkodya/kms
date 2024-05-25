package com.example.kms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Image
import com.example.kms.model.KeyForm
import com.example.kms.model.Operation
import com.example.kms.model.OperationForm
import com.example.kms.model.SignatureForm
import com.example.kms.model.enums.AudienceForm
import com.example.kms.model.enums.AudienceType
import com.example.kms.model.enums.KeyState
import com.example.kms.model.enums.SignalisationType
import com.example.kms.repository.AudienceRepository
import com.example.kms.repository.ImageRepository
import com.example.kms.repository.KeyRepository
import com.example.kms.repository.OperationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SignaturePadViewModel(
    private val repository: OperationRepository,
    private val keyRepository: KeyRepository,
    private val imageRepository: ImageRepository,
    private val audienceRepository: AudienceRepository

) : ViewModel() {
    private val _uiState = MutableStateFlow(SignaturePadUIState())
    val uiState = _uiState.asStateFlow()
    private val _signature = MutableStateFlow(Image(-1, "default", "png"))
    val signature = _signature.asStateFlow()
    fun createOperation(operationForm: OperationForm) {
        viewModelScope.launch {
            try {
                val operation: Operation = repository.createOperation(operationForm)
                _uiState.update {
                    it.copy(operation = operation)
                }
                keyRepository.updateKey(
                    operation.key.key_id ?: 0,
                    KeyForm(
                        audience_id = operation.key.audience.audience_id,
                        key_state = KeyState.GIVEN_AWAY,
                        main = operation.key.main ?: true
                    )
                )
                if (operation.key.audience.signalisation != SignalisationType.NONE) {
                    audienceRepository.update(
                        operation.key.audience.audience_id ?: 0, AudienceForm(
                            number = operation.key.audience.number ?: 0,
                            capacity = operation.key.audience.capacity ?: 0,
                            exist = operation.key.audience.exists ?: true,
                            signalisation = SignalisationType.OFF,
                            audience_type = operation.key.audience.audienceType
                                ?: AudienceType.STUDY,
                            image_id = operation.key.audience.image?.image_id
                        )
                    )
                }

            } catch (e: Exception) {

            }
        }
    }

    fun finishOperation(id: Int) {
        viewModelScope.launch {
            try {
                val operation: Operation = repository.finishOperation(id)
                _uiState.update {
                    it.copy(operation = operation)
                }
                keyRepository.updateKey(
                    operation.key.key_id ?: 0,
                    KeyForm(
                        audience_id = operation.key.audience.audience_id,
                        key_state = KeyState.RETURNED,
                        main = operation.key.main ?: true
                    )
                )

                if (operation.key.audience.signalisation != SignalisationType.NONE) {
                    audienceRepository.update(
                        operation.key.audience.audience_id ?: 0, AudienceForm(
                            number = operation.key.audience.number ?: 0,
                            capacity = operation.key.audience.capacity ?: 0,
                            exist = operation.key.audience.exists ?: true,
                            signalisation = SignalisationType.ON,
                            audience_type = operation.key.audience.audienceType
                                ?: AudienceType.STUDY,
                            image_id = operation.key.audience.image?.image_id
                        )
                    )
                }
            } catch (e: Exception) {

            }
        }
    }


    fun uploadSignature(
        image: RequestBody,
        file: MultipartBody.Part,
        operationId: Int,
        give: Boolean
    ) {
        viewModelScope.launch {
            try {
                val imageSig = imageRepository.upload(imageForm = image, file = file)
                _signature.update {
                    it.copy(
                        image_id = imageSig.image_id,
                        name = imageSig.name,
                        type = imageSig.type
                    )
                }
                imageRepository.createSignature(
                    SignatureForm(
                        _signature.value.image_id,
                        operationId,
                        give,
                    )
                )
            } catch (e: Exception) {

            }
        }
    }
}