package com.example.eazyshop.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "address",
    indices = [Index(value = ["productId"])],
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],  // Đúng: Trùng với khóa chính của Product
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class Address(
    @PrimaryKey val orderId: String = UUID.randomUUID().toString(),
    val productId: Int,  // Liên kết với Product
    val country: String,
    val city: String,
    val district: String,
    val ward: String,
    val specific: String
)
