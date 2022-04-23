package com.zivame.shopping.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.domain.usecases.DeleteCartItemUseCase
import com.zivame.shopping.domain.usecases.GetCartItemsUseCase
import com.zivame.shopping.domain.usecases.InsertCartItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val insetCartItemUseCase: InsertCartItemUseCase
): ViewModel() {

    val cartList = getCartItemsUseCase()

    fun deleteFromCart(cartItem: CartItem){
        viewModelScope.launch(Dispatchers.IO){
            deleteCartItemUseCase(cartItem)
        }
    }

    fun updateCartCart(cartItem: CartItem){
        viewModelScope.launch(Dispatchers.IO){
            insetCartItemUseCase(cartItem)
        }
    }
}