package com.zivame.shopping.data.repository

import androidx.lifecycle.LiveData
import com.zivame.shopping.data.local.CartDao
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
): CartRepository {

    override suspend fun insertIntoCart(cartItem: CartItem) {
        return cartDao.upsert(cartItem)
    }

    override suspend fun deleteFromCart(cartItem: CartItem) {
        return cartDao.delete(cartItem)
    }

    override fun getAllCartItems(): LiveData<List<CartItem>> {
        return cartDao.getAllCartItems()
    }

    override fun getCount(): LiveData<Int> {
        return cartDao.getCount()
    }
}