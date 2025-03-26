package com.example.eazyshop.data.repository

import com.example.eazyshop.data.local.ProductDao
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.data.remote.ProductApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productApi: ProductApi
) {
    val products: Flow<List<Product>> = productDao.getAllProducts()

    suspend fun fetchProducts() {
        val response = productApi.getProducts()
        productDao.insertProducts(response)
    }
}