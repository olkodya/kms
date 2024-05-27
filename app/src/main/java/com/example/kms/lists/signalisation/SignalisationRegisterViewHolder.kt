package com.example.kms.lists.signalisation

import androidx.recyclerview.widget.RecyclerView
import com.example.kms.R
import com.example.kms.model.DTO.Audience
import com.example.kms.model.enums.AudienceType
import com.example.kms.model.enums.SignalisationType
import com.example.kms.utils.Converter

class SignalisationRegisterViewHolder(
    private val binding: com.example.kms.databinding.SignalisationRegisterItemBinding,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(audience: Audience) = with(binding) {
        audienceNumber.text =
            audience.number.toString()

        typeName.text = Converter.convertAudience(audience.audienceType ?: AudienceType.STUDY)
        signalisationStateName.text =
            Converter.convertSignalisation(audience.signalisation ?: SignalisationType.NONE)
        // binding.employeeNumber.text = operation.shift?.watchman?.username
        binding.on.text =
            Converter.convertSignalisation(audience.signalisation ?: SignalisationType.NONE)

        when (audience.signalisation) {
            SignalisationType.NONE -> binding.on.setChipBackgroundColorResource(R.color.my_yellow)
            SignalisationType.OFF -> binding.on.setChipBackgroundColorResource(R.color.my_red)
            SignalisationType.ON -> binding.on.setChipBackgroundColorResource(R.color.my_green)
            null -> TODO()
        }

        binding.signalisationStateName.text = audience.capacity.toString()
        binding.on.isCheckable = true
        binding.on.isChecked = true

        binding.on.isCheckable = false
        itemView.setOnClickListener {
            onClick.invoke(audience.audience_id)
        }
    }

}