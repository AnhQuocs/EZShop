package com.example.eazyshop.data.remote

import com.example.eazyshop.data.model.Product
import retrofit2.http.GET

// Retrofit API (Remote)
// (Tạo Retrofit service để gọi API từ Fake Store)

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<Product>
}

//🔹 Fake Store API cung cấp dữ liệu giả lập cho ứng dụng thương mại điện tử:
//📌 Base URL: https://fakestoreapi.com/
//📌 Danh sách sản phẩm: https://fakestoreapi.com/products