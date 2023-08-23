package com.ispolska.myApplication.task_2.ui.contactAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ispolska.myApplication.task_2.ui.activities.interfaces.UserItemClickListener
import com.example.task_2.databinding.ItemUserBinding
import com.ispolska.myApplication.task_2.data.model.User
import com.ispolska.myApplication.task_2.ui.contactAdapter.utils.MyDiffUtil
import com.ispolska.myApplication.task_2.utils.ext.loadImage


class RecyclerViewAdapter(
    private val listener: UserItemClickListener
) : ListAdapter<User, RecyclerViewAdapter.UsersViewHolder>(MyDiffUtil()) { // TODO: list adapter

    // private var listener: UserItemClickListener? = null

    inner class UsersViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.imageViewDelete.setOnClickListener {
                // val positionUser = holder.adapterPosition // TODO: deprecated
                listener.onUserDelete(user, bindingAdapterPosition)
            }

            with(binding) {
                textViewName.text = user.name
                textViewCareer.text = user.career
                imageViewUserPhoto.loadImage(user.photo)
                Log.d("my_log", user.photo)
            }
        }
    }

    private val users = ArrayList<User>()

    fun setUserItemClickListener(listener: UserItemClickListener) { // TODO: bad
        // this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}