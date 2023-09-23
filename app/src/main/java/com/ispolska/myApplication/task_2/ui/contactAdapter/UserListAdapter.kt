package com.ispolska.myApplication.task_2.ui.contactAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task_2.databinding.ItemUserBinding
import com.ispolska.myApplication.task_2.domain.model.User
import com.ispolska.myApplication.task_2.repository.UserItemClickListener
import com.ispolska.myApplication.task_2.ui.contactAdapter.utils.MyDiffUtil
import com.ispolska.myApplication.task_2.utils.ext.loadImage


class UserListAdapter (private val listener: UserItemClickListener):
        ListAdapter<User, UserListAdapter.UsersViewHolder>(MyDiffUtil()) {


    inner class UsersViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.imageViewDelete.setOnClickListener{
                listener.onUserDelete(user, bindingAdapterPosition)
            }

            with(binding) {
                textViewName.text = user.name
                textViewCareer.text = user.career
                imageViewUserPhoto.loadImage(user.photo)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}