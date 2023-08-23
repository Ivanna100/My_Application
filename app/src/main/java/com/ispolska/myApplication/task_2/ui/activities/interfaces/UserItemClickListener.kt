package com.ispolska.myApplication.task_2.ui.activities.interfaces

import com.ispolska.myApplication.task_2.data.model.User

interface UserItemClickListener {
    fun onUserDelete(user: User, position: Int)
}
