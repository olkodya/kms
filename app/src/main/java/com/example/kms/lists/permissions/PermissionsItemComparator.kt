package com.example.kms.lists.permissions

import androidx.recyclerview.widget.DiffUtil
import com.example.kms.model.Permission


class PermissionsItemComparator : DiffUtil.ItemCallback<Permission>() {
    override fun areItemsTheSame(oldItem: Permission, newItem: Permission): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Permission, newItem: Permission): Boolean {
        return oldItem == newItem
    }

}