package com.example.kms.viewmodels.operations

import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.model.Operation
import com.example.kms.model.enums.KeyState
import com.example.kms.repository.Status

data class OperationsUIState(
    val status: Status = Status.Idle,
    val employee: Employee? = null,
    val key: Key? = null,
    val operation: Operation? = null,
    val hasPermission: Boolean = true
) {
    val isSuccessEmployee: Boolean = status == Status.Idle && (employee != null)
    val isSuccessGiveKey: Boolean =
        status == Status.Idle && (key != null) && key.key_state == KeyState.RETURNED
    val notReturned: Boolean =
        status == Status.Idle && (key != null) && key.key_state != KeyState.RETURNED
    val isSuccessTakeKey: Boolean =
        status == Status.Idle && (key != null) && (key.key_state == KeyState.GIVEN_AWAY) && operation != null
    val notGivenAway: Boolean =
        status == Status.Idle && (key != null) && (key.key_state != KeyState.GIVEN_AWAY)
}