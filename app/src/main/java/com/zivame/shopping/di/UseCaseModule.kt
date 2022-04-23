package com.zivame.shopping.di

import com.zivame.shopping.domain.repository.CartRepository
import com.zivame.shopping.domain.repository.ShoppingListRepository
import com.zivame.shopping.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun getShoppingListISeCase(
        repository: ShoppingListRepository
    ) = GetShoppingListUseCase(repository)

    @Provides
    @Singleton
    fun getCartItemsUseCase(
        repository: CartRepository
    ) = GetCartItemsUseCase(repository)

    @Provides
    @Singleton
    fun deleteCartItemUseCase(
        repository: CartRepository
    ) = DeleteCartItemUseCase(repository)

    @Provides
    @Singleton
    fun insertCartItemUseCase(
        repository: CartRepository
    ) = InsertCartItemUseCase(repository)

    @Provides
    @Singleton
    fun getCountUseCase(
        repository: CartRepository
    ) = GetCountUseCase(repository)
}