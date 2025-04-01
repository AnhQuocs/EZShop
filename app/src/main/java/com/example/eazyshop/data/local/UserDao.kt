package com.example.eazyshop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.eazyshop.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE userId = :id")
    suspend fun getUser(id: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}