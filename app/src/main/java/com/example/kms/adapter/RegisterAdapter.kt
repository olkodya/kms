package com.example.kms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kms.databinding.ShiftRegisterItemBinding
import com.example.kms.model.Operation

class RegisterAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Operation, RegisterViewHolder>(ItemComporator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShiftRegisterItemBinding.inflate(inflater, parent, false)
        val viewHolder = RegisterViewHolder(binding, onClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RegisterViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

}