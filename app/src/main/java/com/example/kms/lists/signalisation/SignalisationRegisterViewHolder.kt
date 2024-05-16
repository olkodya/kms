package com.example.kms.lists.signalisation

import androidx.recyclerview.widget.RecyclerView
import com.example.kms.databinding.SignalisationRegisterItemBinding
import com.example.kms.model.Audience

class SignalisationRegisterViewHolder(
    private val binding: SignalisationRegisterItemBinding,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(audience: Audience) = with(binding) {
        audienceNumber.text =
            audience.number.toString()

        typeName.text = audience.audienceType.toString()
        signalisationState.text = audience.signalisation.toString()
        // binding.employeeNumber.text = operation.shift?.watchman?.username
        itemView.setOnClickListener {
            onClick.invoke(audience.audience_id)
        }
    }

}