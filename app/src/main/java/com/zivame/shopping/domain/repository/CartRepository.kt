package com.zivame.shopping.domain.repository

import androidx.lifecycle.LiveData
import com.zivame.shopping.data.local.CartItem

interface CartRepository {

    suspend fun insertIntoCart(cartItem: CartItem)

    suspend fun deleteFromCart(cartItem: CartItem)

    fun getAllCartItems(): LiveData<List<CartItem>>

    fun getCount(): LiveData<Int>
}