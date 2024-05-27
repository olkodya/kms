package com.example.kms.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.DTO.Operation
import com.example.kms.repository.repos.OperationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class ShiftRegisterViewModel(
    private val repository: OperationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShiftRegisterUIState())
    val uiState: StateFlow<ShiftRegisterUIState> = _uiState.asStateFlow()
    private val _filteredList = MutableStateFlow<List<Operation>>(emptyList())
    val filteredList = _filteredList.asStateFlow()
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    init {
        load()
    }

    fun updateDate(date: String) {
        _date.update {
            date
        }
    }

    fun updateQuery(query: String) {
        _query.update {
            query
        }
    }

    fun load() {
        viewModelScope.launch {
            try {
                val operations: List<Operation> = repository.getAll()
                _uiState.update {
                    it.copy(operations = operations)
                }
                _filteredList.update {
                    _uiState.value.operations
                }

                if (query.value != "" || date.value != "") {
                    filterList(query.value, date.value)
                }
            } catch (e: Exception) {

            }
        }
    }


    fun getById(id: Int) {
        viewModelScope.launch {
            try {
                val operations: List<Operation> = listOf(repository.getById(1))
                _uiState.update {
                    it.copy(operations = operations)
                }
            } catch (e: Exception) {

            }
        }
    }


    fun filterList(query: String?, date: String) {
        if (query != null) {
            filterByQuery(query)
        }

        if (date != "") {
            filterByDate(date)
        }
    }

    fun filterByQuery(query: String) {
        val set = emptySet<Operation>().toMutableSet().toMutableSet()
        for (i in uiState.value.operations) {
            if ((i.employee?.firstName.toString().lowercase(Locale.ROOT)
                    .contains(
                        query.toString().lowercase(Locale.ROOT)
                    )) || (i.employee?.secondName.toString().lowercase(Locale.ROOT)
                    .contains(
                        query.toString().lowercase(Locale.ROOT)
                    )) || (i.employee?.middleName.toString().lowercase(Locale.ROOT)
                    .contains(query.toString().lowercase(Locale.ROOT)))
                || (i.key.audience.number.toString().lowercase(Locale.ROOT)
                    .contains(query.toString().lowercase(Locale.ROOT)))
            ) {
                set += i
            }
        }

        _filteredList.update {
            set.toMutableList()
        }
    }

    fun filterByDate(date: String) {
        val filteredList = emptyList<Operation>().toMutableList()
        for (i in _filteredList.value) {
            if (i.give_date_time.toString().lowercase(Locale.ROOT)
                    .contains(date)
            ) {
                filteredList += i
            }
        }
        _filteredList.update {
            filteredList
        }
    }

}
