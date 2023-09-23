package com.ispolska.myApplication.task_2.ui.activities

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ispolska.myApplication.task_2.domain.localuserdataset.LocalUserData
import com.ispolska.myApplication.task_2.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    init {
        _users.value = LocalUserData().getLocalContactsList()
    }

    fun getUserList(): List<User> = users.value

    fun deleteUser(user: User): Boolean {
        val users2 = _users.value.toMutableList()
        if (users2.contains(user)) {
            users2.remove(user)
            _users.value = users2
            return true
        }
        return false
    }

    fun addUser(user: User, position: Int): Boolean {
        Log.d("add user", "$user")
        val usersCopy = _users.value.toMutableList()
        if (!usersCopy.contains(user)) {
            usersCopy.add(position, user)
            _users.value = usersCopy
            return true
        }
        return false
    }
}