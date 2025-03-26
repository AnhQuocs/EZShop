package com.example.eazyshop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eazyshop.data.model.CartItem
import kotlinx.coroutines.flow.Flow

// (Tạo DAO để quản lý giỏ hàng)

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun removeFromCart(productId: Int)
}