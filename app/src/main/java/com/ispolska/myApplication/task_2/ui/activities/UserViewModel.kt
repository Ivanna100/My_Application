package com.ispolska.myApplication.task_2.ui.activities

import androidx.lifecycle.ViewModel
import com.ispolska.myApplication.task_2.data.localuserdataset.LocalUserData
import com.ispolska.myApplication.task_2.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    // private val users = ArrayList<User>() // TODO: StateFlow
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

// LiveData
//    private val _test: MutableLiveData<User> = MutableLiveData<User>()
//    val test: LiveData<User> get() = _test

    init {
        _users.value = LocalUserData().getLocalContactsList()
//            .toMutableList().apply {
//            addAll(LocalUserData().getLocalContactsList())
//        }
    }

    fun getUserList(): List<User> = users.value

    fun deleteUser(user: User): Boolean {
        val users2 = _users.value.toMutableList()
        if (users2.contains(user)) {
//            _users.value.toMutableList().apply {
                users2.remove(user)
            _users.value = users2
//            }
            return true
        }
        return false
    }

    fun addUser(user: User, position: Int): Boolean {
        val usersCopy = _users.value.toMutableList()
        if (!usersCopy.contains(user)) {
//            _users.value.toMutableList().apply {
                usersCopy.add(position, user)
            _users.value = usersCopy
//            }
            return true
        }
        return false
    }
}