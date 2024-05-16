package com.example.kms.viewmodels.profile

import com.example.kms.model.ShiftUIModel
import com.example.kms.model.Watch
import com.example.kms.repository.Status

data class ProfileUiState (
    val shift: ShiftUIModel? = null,
    val status: Status = Status.Idle,
    val image: ByteArray? = null,
    val watches: List<Watch> = emptyList(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileUiState

        if (shift != other.shift) return false
        if (status != other.status) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (watches != other.watches) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shift?.hashCode() ?: 0
        result = 31 * result + status.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + watches.hashCode()
        return result
    }
}