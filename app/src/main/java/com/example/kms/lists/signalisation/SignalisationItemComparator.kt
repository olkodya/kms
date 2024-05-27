package com.example.kms.lists.signalisation

import androidx.recyclerview.widget.DiffUtil
import com.example.kms.model.DTO.Audience

class SignalisationItemComparator : DiffUtil.ItemCallback<Audience>() {
    override fun areItemsTheSame(oldItem: Audience, newItem: Audience): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Audience, newItem: Audience): Boolean {
        return oldItem == newItem
    }

}