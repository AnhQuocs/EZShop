package com.example.eazyshop.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    @DrawableRes val image: Int,  // Dùng Int thay vì String
    val description: String,
    val category: String
)