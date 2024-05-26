package com.example.kms.lists.permissions

import androidx.recyclerview.widget.RecyclerView
import com.example.kms.databinding.PermissionItemBinding
import com.example.kms.model.Permission

class PermissionsViewHolder(
    private val binding: PermissionItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(permission: Permission) = with(binding) {
        permissionName.text = permission.name
    }

}