package com.example.kms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kms.databinding.ShiftRegisterItemBinding
import com.example.kms.model.Operation


class RegisterViewHolder(private val binding: ShiftRegisterItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(operation: Operation) = with(binding) {
        audienceNumber.text = operation.key.key_id.toString()
        binding.giveDate.text = operation.give_date_time
        binding.takeDate.text = operation.return_date_time ?: "Ключ не был возвращен"
        binding.employeeNumber.text = operation.shift.watchman.username
    }


    companion object {
        fun create(parent: ViewGroup) :RegisterViewHolder {
            return RegisterViewHolder(
                ShiftRegisterItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}