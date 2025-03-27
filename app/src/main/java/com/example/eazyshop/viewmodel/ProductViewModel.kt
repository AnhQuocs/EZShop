package com.example.eazyshop.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.eazyshop.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val products = productRepository.products.asLiveData() // Lấy dữ liệu từ repository và convert thành LiveData

    init {
        viewModelScope.launch {
            val count = productRepository.getProductCount()
            Log.d("ProductViewModel", "Số sản phẩm trong database trước khi fetch: $count")

            if (count == 0) {
                Log.d("ProductViewModel", "Database trống -> Gọi API để fetch sản phẩm")
                productRepository.fetchProducts()
            } else {
                Log.d("ProductViewModel", "Database đã có dữ liệu -> Không gọi API")
            }
        }
    }

}
