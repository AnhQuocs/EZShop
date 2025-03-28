package com.example.eazyshop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eazyshop.data.model.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address)

    @Query("SELECT * FROM address WHERE productId = :productId")
    suspend fun getAddressByProductId(productId: String): List<Address>
}
