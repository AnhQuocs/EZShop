package com.example.eazyshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eazyshop.data.model.User
import com.example.eazyshop.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    suspend fun getUser(id: Int) {
        val fetchedUser: User = userRepository.getUser(id) ?: run {
            val defaultUser = User(
                userId = id,
                userName = "User",
                avatarUri = null,
                phoneNumber = "0000000000"
            )
            userRepository.insertUser(defaultUser)
            defaultUser
        }
        _user.value = fetchedUser
    }

    suspend fun updateUser(user: User) {
        userRepository.updateUser(user)
    }
}