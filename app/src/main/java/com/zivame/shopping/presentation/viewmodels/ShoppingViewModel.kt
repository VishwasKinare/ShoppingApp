package com.zivame.shopping.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zivame.shopping.R
import com.zivame.shopping.core.ApiState
import com.zivame.shopping.core.Status
import com.zivame.shopping.data.dto.ShoppingListDto
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.domain.usecases.GetCountUseCase
import com.zivame.shopping.domain.usecases.GetShoppingListUseCase
import com.zivame.shopping.domain.usecases.InsertCartItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val insetCartItemUseCase: InsertCartItemUseCase,
    private val getCountUseCase: GetCountUseCase
): ViewModel() {

    private val _shoppingList = MutableLiveData<ApiState<ShoppingListDto>>()
    val shoppingList: LiveData<ApiState<ShoppingListDto>> = _shoppingList

    val cartItemCount = getCountUseCase()

    init {
        getProducts()
    }

    private fun getProducts(){
        getShoppingListUseCase().onEach { result ->
            when(result.status){
                Status.SUCCESS ->{
                    _shoppingList.postValue(ApiState(data = result.data))
                }
                Status.ERROR -> {
                    _shoppingList.postValue(ApiState(error = result.message ?: R.string.unable_to_process_request))
                }
                Status.LOADING -> {
                    _shoppingList.postValue(ApiState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope + Dispatchers.IO)
    }

    fun addToCart(cartItem: CartItem){
        viewModelScope.launch(Dispatchers.IO){
            insetCartItemUseCase(cartItem)
        }
    }
}