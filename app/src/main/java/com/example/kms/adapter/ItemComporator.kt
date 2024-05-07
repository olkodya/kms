package com.example.kms.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.kms.model.Operation

class ItemComporator: DiffUtil.ItemCallback<Operation>() {
    override fun areItemsTheSame(oldItem: Operation, newItem: Operation): Boolean {
        return oldItem.operation_id == newItem.operation_id
    }

    override fun areContentsTheSame(oldItem: Operation, newItem: Operation): Boolean {
        return oldItem == newItem
    }

}