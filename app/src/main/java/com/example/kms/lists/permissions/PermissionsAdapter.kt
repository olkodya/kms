package com.example.kms.lists.divisions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kms.databinding.PermissionItemBinding
import com.example.kms.lists.permissions.PermissionsItemComparator
import com.example.kms.lists.permissions.PermissionsViewHolder
import com.example.kms.model.Permission

class PermissionsAdapter() :
    ListAdapter<Permission, PermissionsViewHolder>(PermissionsItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PermissionItemBinding.inflate(inflater, parent, false)
        val viewHolder = PermissionsViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PermissionsViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

}