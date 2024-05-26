package com.example.kms.lists.divisions

import androidx.recyclerview.widget.RecyclerView
import com.example.kms.databinding.DivisionItemBinding
import com.example.kms.model.Division

class DivisionsViewHolder(
    private val binding: DivisionItemBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(division: Division) = with(binding) {
        divisionName.text = division.name
    }

}