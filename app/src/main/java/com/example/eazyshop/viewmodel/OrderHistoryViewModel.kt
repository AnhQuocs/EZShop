package com.example.eazyshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eazyshop.data.model.OrderHistory
import com.example.eazyshop.data.repository.OrderHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val repository: OrderHistoryRepository
) : ViewModel() {

    fun getOrdersByUser(userId: String): Flow<List<OrderHistory>> =
        repository.getOrdersByUser(userId)

    fun insertOrder(orderHistory: OrderHistory) {
        viewModelScope.launch {
            repository.insertOrder(orderHistory)
        }
    }
}
