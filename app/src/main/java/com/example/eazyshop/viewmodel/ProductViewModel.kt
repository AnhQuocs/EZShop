package com.example.eazyshop.viewmodel


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
            productRepository.fetchProducts() // Gọi API để fetch dữ liệu khi ViewModel được tạo
        }
    }
}
