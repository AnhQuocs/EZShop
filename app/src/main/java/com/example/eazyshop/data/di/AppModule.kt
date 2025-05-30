package com.example.eazyshop.data.di

import android.content.Context
import com.example.eazyshop.data.local.AddressDao
import com.example.eazyshop.data.local.AppDatabase
import com.example.eazyshop.data.local.CartDao
import com.example.eazyshop.data.local.OrderHistoryDao
import com.example.eazyshop.data.local.ProductDao
import com.example.eazyshop.data.local.UserDao
import com.example.eazyshop.data.model.User
import com.example.eazyshop.data.remote.ProductApi
import com.example.eazyshop.data.repository.AddressRepository
import com.example.eazyshop.data.repository.CartRepository
import com.example.eazyshop.data.repository.OrderHistoryRepository
import com.example.eazyshop.data.repository.ProductRepository
import com.example.eazyshop.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Hilt Dependency Injection (AppModule.kt)
// Để inject các dependencies (Database, API, Repository) vào ViewModel:

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Provides
    fun provideCartDao(database: AppDatabase): CartDao = database.cartDao()

    @Provides
    fun provideAddressDao(database: AppDatabase): AddressDao = database.addressDao()

    @Provides
    fun provideOrderHistoryDao(database: AppDatabase): OrderHistoryDao = database.orderHistoryDao()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideProductRepository(productDao: ProductDao, productApi: ProductApi): ProductRepository =
        ProductRepository(productDao, productApi)

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository =
        CartRepository(cartDao)

    @Provides
    @Singleton
    fun provideAddressRepository(addressDao: AddressDao): AddressRepository =
        AddressRepository(addressDao)

    @Provides
    @Singleton
    fun provideOrderHistoryRepository(orderHistoryDao: OrderHistoryDao): OrderHistoryRepository {
        return OrderHistoryRepository(orderHistoryDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepository(userDao)
}