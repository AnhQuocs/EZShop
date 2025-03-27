package com.example.eazyshop.data.repository

import com.example.eazyshop.data.local.ProductDao
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.data.remote.ProductApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import android.util.Log

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productApi: ProductApi
) {
    val products: Flow<List<Product>> = productDao.getAllProducts()

    suspend fun fetchProducts() {
        Log.d("ProductRepository", "Số sản phẩm trong database: ${productDao.count()}")
        if (productDao.count() == 0) {
            val response = productApi.getProducts()
            Log.d("ProductRepository", "Fetching từ API: ${response.size} sản phẩm")
            productDao.insertProducts(response)
        }
    }

    suspend fun getProductCount(): Int {
        return productDao.count()
    }
}