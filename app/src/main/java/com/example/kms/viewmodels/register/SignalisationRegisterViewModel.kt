package com.example.kms.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.DTO.Audience
import com.example.kms.model.enums.AudienceType
import com.example.kms.model.enums.SignalisationType
import com.example.kms.repository.repos.AudienceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class SignalisationRegisterViewModel(private val repository: AudienceRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SignalisationRegisterUIState())
    val uiState: StateFlow<SignalisationRegisterUIState> = _uiState.asStateFlow()
    private val _filteredListTmp = MutableStateFlow<List<Audience>>(emptyList())
    val filteredListTmp = _filteredListTmp.asStateFlow()
    private val _filteredList = MutableStateFlow<List<Audience>>(emptyList())
    val filteredList = _filteredListTmp.asStateFlow()
    private val _signalState = MutableStateFlow<List<Int>>(emptyList())
    val signalState = _signalState.asStateFlow()
    private val _audienceType = MutableStateFlow<List<Int>>(emptyList())
    val audienceType = _audienceType.asStateFlow()
    private val _startCapacity = MutableStateFlow<Int>(-1)
    val startCapacity = _startCapacity.asStateFlow()
    private val _endCapacity = MutableStateFlow<Int>(-1)
    val endCapacity = _endCapacity.asStateFlow()
    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()


    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            try {
                var audiences: List<Audience> = repository.getAudiences()
                audiences = audiences.sortedBy {
                    it.number
                }
                _uiState.update {
                    it.copy(audiences = audiences)
                }
                _filteredListTmp.update { uiState.value.audiences }
                _filteredList.update { uiState.value.audiences }
                filterList(
                    query.value,
                    signalState.value,
                    audienceType.value,
                    startCapacity.value,
                    endCapacity.value
                )

            } catch (e: Exception) {

            }
        }
    }

    fun filterList(
        query: String?,
        signalState: List<Int>,
        audienceType: List<Int>,
        startCapacity: Int,
        endCapacity: Int,
    ) {
        if (query != null) {
            filterByQuery(query)
        }
        if (_startCapacity.value != -1 && _endCapacity.value != -1) {
            filterByCapacity(startCapacity, endCapacity)
        }

        if (_audienceType.value.isNotEmpty()) {
            filterByAudienceType(audienceType)
        }

        if (_signalState.value.isNotEmpty()) {
            filterBySignalState(signalState)
        }

        _filteredList.update {
            filteredListTmp.value
        }


    }

    fun filterByQuery(query: String) {
        val set = emptySet<Audience>().toMutableSet().toMutableSet()
        for (i in uiState.value.audiences) {
            if ((i.number.toString().lowercase(Locale.ROOT)
                    .contains(
                        query.toString().lowercase(Locale.ROOT)
                    ))
            ) {
                set += i
            }
        }

        _filteredListTmp.update {
            set.toMutableList()
        }
    }

    private fun filterByCapacity(startCapacity: Int, endCapacity: Int) {
        val list: MutableList<Audience> = emptyList<Audience>().toMutableList()
        for (i in _filteredListTmp.value) {
            if (i.capacity!! >= startCapacity && i.capacity <= endCapacity) {
                list += i
            }
        }
        _filteredListTmp.update {
            list
        }

    }

    private fun filterByAudienceType(audienceType: List<Int>) {
        val audienceList: MutableList<Audience> = emptyList<Audience>().toMutableList()
        val list: MutableList<AudienceType> = emptyList<AudienceType>().toMutableList()
        for (i in audienceType) {
            when (i) {
                0 -> list += AudienceType.STUDY
                1 -> list += AudienceType.MULTIMEDIA
                2 -> list += AudienceType.LAB
                3 -> list += AudienceType.ADMINISTRATION
            }
        }

        for (i in _filteredListTmp.value) {
            if (list.contains(i.audienceType)) {
                audienceList += i
            }
        }
        _filteredListTmp.update {
            audienceList
        }
    }

    private fun filterBySignalState(signalState: List<Int>) {
        val signalList: MutableList<Audience> = emptyList<Audience>().toMutableList()
        val list: MutableList<SignalisationType> = emptyList<SignalisationType>().toMutableList()
        for (i in signalState) {
            when (i) {
                0 -> list += SignalisationType.ON
                1 -> list += SignalisationType.OFF
                2 -> list += SignalisationType.NONE
            }
        }

        for (i in _filteredListTmp.value) {
            if (list.contains(i.signalisation)) {
                signalList += i
            }
        }
        _filteredListTmp.update {
            signalList
        }
    }

    fun updateQuery(query: String) {
        _query.update {
            query
        }
    }

    fun updateCapacity(startCapacity: Int, endCapacity: Int) {
        _startCapacity.update {
            startCapacity
        }

        _endCapacity.update {
            endCapacity
        }
    }

    fun updateAudienceType(audienceType: List<Int>) {
        _audienceType.update {
            audienceType
        }
    }

    fun updateSignalState(signalState: List<Int>) {
        _signalState.update {
            signalState
        }
    }

}