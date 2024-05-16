package com.example.kms.lists.shifts

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
        binding.employeeNumber.text =
            operation.employee?.second_name + " " + operation.employee?.first_name + " " + operation.employee?.middle_name
        itemView.setOnClickListener {
            onClick.invoke(operation.operation_id)
        }
    }

}