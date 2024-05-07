package com.example.kms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kms.databinding.ActivityMainBinding
import com.example.kms.databinding.ShiftRegisterItemBinding
import com.example.kms.model.Operation

class RegisterAdapter : ListAdapter<Operation, RegisterViewHolder>(ItemComporator()) {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {



        val inflater = LayoutInflater.from(parent.context)
        val binding = ShiftRegisterItemBinding.inflate(inflater, parent, false)
        val viewHolder = RegisterViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RegisterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}