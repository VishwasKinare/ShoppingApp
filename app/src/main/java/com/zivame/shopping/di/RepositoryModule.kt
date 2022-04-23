package com.zivame.shopping.di

import com.zivame.shopping.data.local.CartDao
import com.zivame.shopping.data.remote.ShoppingApi
import com.zivame.shopping.data.repository.CartRepositoryImpl
import com.zivame.shopping.data.repository.ShoppingListRepositoryImpl
import com.zivame.shopping.domain.repository.CartRepository
import com.zivame.shopping.domain.repository.ShoppingListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun getShoppingRepository(
        api: ShoppingApi
    ): ShoppingListRepository = ShoppingListRepositoryImpl(api)

    @Provides
    @Singleton
    fun getCartRepository(
        cartDao: CartDao
    ): CartRepository = CartRepositoryImpl(cartDao)
}