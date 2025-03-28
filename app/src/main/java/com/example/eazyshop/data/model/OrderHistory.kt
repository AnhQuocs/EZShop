package com.example.eazyshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "order_history")
data class OrderHistory(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val productId: Int,
    val addressId: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)