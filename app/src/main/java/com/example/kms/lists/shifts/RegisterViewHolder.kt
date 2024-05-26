package com.example.kms.lists.shifts

import androidx.recyclerview.widget.RecyclerView
import com.example.kms.databinding.ShiftRegisterItemBinding
import com.example.kms.model.Operation
import com.example.kms.utils.Converter.convertDateFormat


class RegisterViewHolder(
    private val binding: ShiftRegisterItemBinding,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(operation: Operation) = with(binding) {
        audienceNumber.text =
            operation.shift?.watch?.buildingNumber.toString() + "-" + operation.key.audience.number.toString()
        binding.giveDate.text = convertDateFormat(operation.give_date_time ?: "")
        if (operation.return_date_time != null)
            binding.takeDate.text =
                convertDateFormat(operation.return_date_time)
        else {
            binding.takeDate.text = "Ключ не был возвращен"
        }
        binding.employeeNumber.text =
            operation.employee?.secondName + " " + operation.employee?.firstName + " " + operation.employee?.middleName
        itemView.setOnClickListener {
            onClick.invoke(operation.operation_id)
        }
    }

}