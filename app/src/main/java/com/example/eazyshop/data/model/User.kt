package com.example.eazyshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey val userId: Int = 1,
    val userName: String = "User",
    val avatarUri: String? = null, // Lưu URI thay vì resource ID
    val phoneNumber: String
)