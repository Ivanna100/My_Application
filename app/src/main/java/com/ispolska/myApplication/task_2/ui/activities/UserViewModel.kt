package com.ispolska.myApplication.task_2.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ispolska.myApplication.task_2.data.localuserdataset.LocalUserData
import com.ispolska.myApplication.task_2.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    // private val users = ArrayList<User>() // TODO: StateFlow
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val _test: MutableLiveData<User> = MutableLiveData<User>()
    val test: LiveData<User> get() = _test

    init {
        _users.value.toMutableList().apply {
            addAll(LocalUserData().getLocalContactsList())
        }
    }

    fun getUserList(): List<User> = users.value

    fun deleteUser(user: User): Boolean {
        if (_users.value.contains(user)) {
            _users.value.toMutableList().apply {
                remove(user)
            }
            return true
        }
        return false
    }

    fun addUser(user: User, position: Int): Boolean {
        if (!_users.value.contains(user)) {
            _users.value.toMutableList().apply {
                add(position, user)
            }
            return true
        }
        return false
    }
}