package com.example.eazyshop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eazyshop.data.model.OrderHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderHistory: OrderHistory)

    @Query("SELECT * FROM order_history WHERE productId = :productId ORDER BY createdAt DESC")
    fun getOrdersByUser(productId: String): Flow<List<OrderHistory>>

    @Query("SELECT * FROM order_history WHERE id = :orderId LIMIT 1")
    suspend fun getOrderById(orderId: String): OrderHistory?
}