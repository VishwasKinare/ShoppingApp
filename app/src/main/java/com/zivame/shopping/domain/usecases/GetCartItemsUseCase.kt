package com.zivame.shopping.domain.usecases

import androidx.lifecycle.LiveData
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.domain.repository.CartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
)  {

    operator fun invoke(): LiveData<List<CartItem>> {
        return repository.getAllCartItems()
    }
}