package com.example.eazyshop.data.repository

import com.example.eazyshop.data.local.OrderHistoryDao
import com.example.eazyshop.data.model.OrderHistory
import kotlinx.coroutines.flow.Flow

class OrderHistoryRepository(private val orderHistoryDao: OrderHistoryDao) {
    fun getOrdersBYProductId(productId: Int): Flow<List<OrderHistory>> =
        orderHistoryDao.getOrdersByProductId(productId)

    suspend fun insertOrder(order: OrderHistory) {
        orderHistoryDao.insertOrder(order)
    }
}
