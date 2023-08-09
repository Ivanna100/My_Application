package com.ispolska.myApplication.task_2.repository

import com.ispolska.myApplication.task_2.domain.model.User

interface UserItemClickListener {
    fun onUserDelete(user: User, position: Int)
}
