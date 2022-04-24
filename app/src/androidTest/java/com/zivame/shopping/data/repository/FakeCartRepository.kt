package com.zivame.shopping.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.domain.repository.CartRepository

class FakeCartRepository: CartRepository {

    private val cartItems = mutableListOf<CartItem>()

    private val observableCartItems = MutableLiveData<List<CartItem>>(cartItems)
    private val observableCartItemCount = MutableLiveData<Int>()

    private fun refreshLiveDate(){
        observableCartItems.postValue(cartItems)
        observableCartItemCount.postValue(cartItems.count())
    }

    override suspend fun insertIntoCart(cartItem: CartItem) {
        cartItems.add(cartItem)
        refreshLiveDate()
    }

    override suspend fun deleteFromCart(cartItem: CartItem) {
        cartItems.remove(cartItem)
        refreshLiveDate()
    }

    override fun getAllCartItems(): LiveData<List<CartItem>> {
        return observableCartItems
    }

    override fun getCount(): LiveData<Int> {
        return observableCartItemCount
    }
}