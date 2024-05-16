package com.example.kms.lists.shifts

import androidx.recyclerview.widget.DiffUtil
import com.example.kms.model.Operation

class ItemComporator : DiffUtil.ItemCallback<Operation>() {
    override fun areItemsTheSame(oldItem: Operation, newItem: Operation): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Operation, newItem: Operation): Boolean {
        return oldItem == newItem
    }

}