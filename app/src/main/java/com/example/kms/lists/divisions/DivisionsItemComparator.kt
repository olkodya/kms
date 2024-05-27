package com.example.kms.lists.divisions

import androidx.recyclerview.widget.DiffUtil
import com.example.kms.model.DTO.Division

class DivisionsItemComparator : DiffUtil.ItemCallback<Division>() {
    override fun areItemsTheSame(oldItem: Division, newItem: Division): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Division, newItem: Division): Boolean {
        return oldItem == newItem
    }

}