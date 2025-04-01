package com.example.eazyshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.eazyshop.data.model.OrderHistory
import com.example.eazyshop.data.repository.OrderHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val historyRepository: OrderHistoryRepository
) : ViewModel() {

    val orderHistories = historyRepository.historyItems.asLiveData()

    fun getOrdersByProductId(productId: Int): Flow<List<OrderHistory>> =
        historyRepository.getOrdersBYProductId(productId)

    fun insertOrder(orderHistory: OrderHistory) {
        viewModelScope.launch {
            historyRepository.insertOrder(orderHistory)
        }
    }
}
