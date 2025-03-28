package com.example.eazyshop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.eazyshop.Converters
import com.example.eazyshop.R
import com.example.eazyshop.data.model.Address
import com.example.eazyshop.data.model.CartItem
import com.example.eazyshop.data.model.OrderHistory
import com.example.eazyshop.data.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// (Database chính của ứng dụng)

@Database(entities = [Product::class, CartItem::class, Address::class, OrderHistory::class], version = 9, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun addressDao(): AddressDao
    abstract fun orderHistoryDao(): OrderHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecommerce_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) // Thêm Callback
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.productDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(productDao: ProductDao) {
            val sampleProducts = listOf(
                Product(1, "Laptop Gaming", 1299.99, R.drawable.laptop_gm, "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W", "Electronics", 1),
                Product(2, "Điện thoại", 899.99, R.drawable.infinix, "Infinix Hot 50 Pro Plus 8GB 256GB", "Electronics", 1),
                Product(3, "Tai nghe", 199.99, R.drawable.headphone_bt, "Tai nghe Bluetooth True Wireless Samsung Galaxy", "Accessories", 1),
                Product(4, "Burger", 199.99, R.drawable.food1, "Hamburger", "Food", 1),
                Product(5, "Pizza", 199.99, R.drawable.food2, "Pizza", "Food", 1),
                Product(6, "Food", 199.99, R.drawable.food3, "Fish", "Food", 1),
                Product(7, "Pizza 2", 199.99, R.drawable.food4, "Pizza 2", "Food", 1),
            )
            productDao.insertAll(sampleProducts)
        }
    }
}
