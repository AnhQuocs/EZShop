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

@Database(entities = [Product::class, CartItem::class, Address::class, OrderHistory::class], version = 16, exportSchema = false)
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
                Product(1, "iPhone", 1239.99, R.drawable.phone2, "iPhone 16 Pro Max 256GB | Genuine VN/A", "Phone", 1),
                Product(2, "Samsung Ody", 235.99, R.drawable.screen3, "Samsung Odyssey LS27DG502EEXXV Gaming Monitor", "Screen", 1),
                Product(3, "CPS X MSI", 819.99, R.drawable.pc4, "PC CPS X MSI Gaming Intel i5 Gen 12 With Monitor", "PC", 1),
                Product(4, "Infinix", 195.99, R.drawable.phone6, "Infinix Hot 50 Pro Plus 8GB 256GB", "Phone", 1),
                Product(5, "Gigabyte", 1035.99, R.drawable.laptop5, "Laptop Gigabyte G6 MF-H2VN854KH", "Laptop", 1),
                Product(6, "ViewSonic", 207.99, R.drawable.screen6, "ViewSonic VX2758A-2K-PRO-2 27-inch Gaming", "Screen", 1),
                Product(7, "E-Dra", 87.99, R.drawable.screen4, "E-Dra EGM27F100H 27 inch monitor", "Screen", 1),
                Product(8, "CPS", 5999.99, R.drawable.pc2, "PC CPS Quantum Blaze 5090", "PC", 1),
                Product(9, "Macbook", 915.99, R.drawable.laptop1, "Apple MacBook Air M2 2024 8CPU 8GPU 16GB 256GB", "Laptop", 1),
                Product(10, "ASUS", 579.99, R.drawable.phone5, "ASUS ROG Phone 6 12GB 256GB", "Phone", 1),
                Product(11, "Lenovo", 835.99, R.drawable.laptop6, "Laptop Lenovo LOQ 15IAX9 83GS001RVN", "Laptop", 1),
                Product(12, "Vivo", 259.99, R.drawable.phone4, "Vivo Y100", "Phone", 1),
                Product(13, "LG UltraGear", 107.99, R.drawable.screen2, "LG UltraGear 24GS50F-B 24-inch Gaming Monitor", "Screen", 1),
                Product(14, "CPS X ASUS", 799.99, R.drawable.pc1, "PC CPS X ASUS Gaming Intel i5 Gen 14 With Monitor", "PC", 1),
                Product(15, "Samsung", 1119.99, R.drawable.phone1, "Samsung Galaxy S25 Ultra 12GB 256GB", "Phone", 1),
                Product(16, "OPPO", 439.99, R.drawable.phone3, "OPPO Reno10 Pro+ 5G 12GB 256GB", "Phone", 1),
                Product(17, "CPS Office", 359.99, R.drawable.pc3, "Intel i5 Gen 12 Office PC CPS - With Monitor", "PC", 1),
                Product(18, "ASUS Vivobook", 675.99, R.drawable.laptop2, "Laptop ASUS Vivobook 14 OLED A1405VA-KM257W", "Laptop", 1),
                Product(19, "ASUS TUF", 123.99, R.drawable.screen1, "ASUS TUF VG249Q3A 24-inch Gaming Monitor", "Screen", 1),
                Product(20, "MSI", 479.99, R.drawable.laptop3, "Laptop MSI Modern 14 C12MO-660VN", "Laptop", 1),
                Product(21, "Xiaomi", 179.99, R.drawable.screen5, "Xiaomi G27QI 27 inch Gaming Monitor", "Screen", 1),
                Product(22, "Acer", 751.99, R.drawable.laptop4, "Laptop Gaming Acer Nitro 5 Tiger AN515 58 52SP", "Laptop", 1)
            )
            productDao.insertAll(sampleProducts)
        }
    }
}
