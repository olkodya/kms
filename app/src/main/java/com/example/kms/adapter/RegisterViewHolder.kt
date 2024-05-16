package com.example.kms.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.kms.databinding.ShiftRegisterItemBinding
import com.example.kms.model.Operation


class RegisterViewHolder(
    private val binding: ShiftRegisterItemBinding,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(operation: Operation) = with(binding) {
        audienceNumber.text =
            operation.shift?.watch?.building_number.toString() + "-" + operation.key.audience.number.toString()
        binding.giveDate.text = operation.give_date_time
        binding.takeDate.text = operation.return_date_time ?: "Ключ не был возвращен"
        // binding.employeeNumber.text = operation.shift?.watchman?.username
        itemView.setOnClickListener {
            onClick.invoke(operation.operation_id)
        }
    }

}