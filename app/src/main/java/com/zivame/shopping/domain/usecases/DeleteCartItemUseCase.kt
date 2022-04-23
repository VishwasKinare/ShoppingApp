package com.zivame.shopping.domain.usecases

import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.domain.repository.CartRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val repository: CartRepository
)  {
    suspend operator fun invoke(cartItem: CartItem){
        repository.deleteFromCart(cartItem)
    }
}