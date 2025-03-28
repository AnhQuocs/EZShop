package com.example.eazyshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eazyshop.data.model.Address
import com.example.eazyshop.data.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {

    fun saveAddress(address: Address) {
        viewModelScope.launch {
            addressRepository.saveAddress(address)
        }
    }
}
