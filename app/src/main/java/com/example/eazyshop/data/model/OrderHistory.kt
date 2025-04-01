package com.example.eazyshop.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(
    tableName = "order_history",
    foreignKeys = [
        ForeignKey(
            entity = Address::class,
            parentColumns = ["orderId"],
            childColumns = ["addressId"],
            onDelete = ForeignKey.CASCADE // Nếu xóa Address, OrderHistory cũng bị xóa
        )
    ]
)

data class OrderHistory(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val productId: Int,
    val title: String,
    val description: String,
    val price: Float,
    val quantity: Int,
    val addressId: String,      // Giờ đã trỏ đến orderId của Address
    val createdAt: LocalDateTime = LocalDateTime.now()
)