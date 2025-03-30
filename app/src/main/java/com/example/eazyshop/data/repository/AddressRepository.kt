package com.example.eazyshop.data.repository

import com.example.eazyshop.data.local.AddressDao
import com.example.eazyshop.data.model.Address
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val addressDao: AddressDao
) {
    suspend fun saveAddress(address: Address) {
        addressDao.insertAddress(address)
    }

    fun getAddress(productId: Int): Flow<List<Address>> {
        return addressDao.getAddressByProductId(productId)
    }
}