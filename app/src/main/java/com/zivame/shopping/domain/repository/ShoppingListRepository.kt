package com.zivame.shopping.domain.repository

import com.zivame.shopping.data.dto.ShoppingListDto
import retrofit2.Response

interface ShoppingListRepository  {
    suspend fun getShoppingListItems(): Response<ShoppingListDto>
}