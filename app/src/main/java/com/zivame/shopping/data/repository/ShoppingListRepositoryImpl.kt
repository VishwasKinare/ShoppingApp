package com.zivame.shopping.data.repository

import androidx.lifecycle.LiveData
import com.zivame.shopping.data.dto.ShoppingListDto
import com.zivame.shopping.data.local.CartDao
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.data.remote.ShoppingApi
import com.zivame.shopping.domain.repository.ShoppingListRepository
import retrofit2.Response
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingApi: ShoppingApi
): ShoppingListRepository {

    override suspend fun getShoppingListItems(): Response<ShoppingListDto> {
        return shoppingApi.getCoinList()
    }
}