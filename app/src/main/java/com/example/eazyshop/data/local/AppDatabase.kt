package com.example.eazyshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eazyshop.data.model.CartItem
import com.example.eazyshop.data.model.Product

// (Database chính của ứng dụng)

@Database(entities = [Product::class, CartItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}