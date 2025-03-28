package com.example.eazyshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eazyshop.data.model.CartItem
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Test đơn giản để kiểm tra dữ liệu

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    fun addToCart(product: Product) {
        viewModelScope.launch {
            val cartItem = CartItem(
                productId = product.id,
                title = product.title,
                price = product.price,
                image = product.image,
                category = product.category,
                quantity = product.quantity
            )
            cartRepository.addToCart(cartItem)
        }
    }
}