package com.example.kms.lists.divisions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kms.databinding.DivisionItemBinding
import com.example.kms.model.Division

class DivisionsAdapter() :
    ListAdapter<Division, DivisionsViewHolder>(DivisionsItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivisionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DivisionItemBinding.inflate(inflater, parent, false)
        val viewHolder = DivisionsViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: DivisionsViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

}