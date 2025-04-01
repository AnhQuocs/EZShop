package com.example.eazyshop.data.repository

import com.example.eazyshop.data.local.UserDao
import com.example.eazyshop.data.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
){
    suspend fun getUser(id: Int): User? {
        return userDao.getUser(id)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
}