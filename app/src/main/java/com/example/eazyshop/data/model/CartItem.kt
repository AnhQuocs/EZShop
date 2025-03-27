package com.example.eazyshop.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    @DrawableRes val image: Int,  // Dùng Int thay vì String,
    val category: String,
    val quantity: Int,
)