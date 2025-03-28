package com.example.eazyshop.data.repository

import com.example.eazyshop.data.local.CartDao
import com.example.eazyshop.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    val cartItems: Flow<List<CartItem>> = cartDao.getCartItems()

    suspend fun addToCart(cartItem: CartItem) {
        cartDao.addToCart(cartItem)
    }

    suspend fun removeFromCart(productId: Int) {
        cartDao.removeFromCart(productId)
    }
}