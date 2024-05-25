package com.example.kms.lists.signalisation

import androidx.recyclerview.widget.RecyclerView
import com.example.kms.model.Audience
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

        itemView.setOnClickListener {
            onClick.invoke(audience.audience_id)
        }
    }

}