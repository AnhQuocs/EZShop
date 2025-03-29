package com.example.eazyshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.eazyshop.data.model.CartItem
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Test đơn giản để kiểm tra dữ liệu

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItems = cartRepository.cartItems.asLiveData()

    // Flow để phát sự kiện khi thêm vào giỏ hàng
    private val _cartEvent = MutableSharedFlow<String>()
    val cartEvent = _cartEvent.asSharedFlow()

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val cartItem = CartItem(
                productId = product.id,
                title = product.title,
                price = product.price,
                image = product.image,
                description = product.description,
                category = product.category,
                quantity = product.quantity
            )
            cartRepository.addToCart(cartItem)

            _cartEvent.emit("Add to cart successfully! 🛒")
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId = cartItem.productId)
        }
    }
}