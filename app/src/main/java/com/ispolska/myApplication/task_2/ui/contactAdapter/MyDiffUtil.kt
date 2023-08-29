package com.ispolska.myApplication.task_2.ui.contactAdapter

import androidx.recyclerview.widget.DiffUtil
import com.ispolska.myApplication.task_2.data.model.User

class MyDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
}