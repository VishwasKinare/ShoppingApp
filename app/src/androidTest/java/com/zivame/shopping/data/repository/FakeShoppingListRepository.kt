package com.zivame.shopping.data.repository

import androidx.lifecycle.MutableLiveData
import com.zivame.shopping.data.dto.Product
import com.zivame.shopping.data.dto.ShoppingListDto
import com.zivame.shopping.domain.repository.ShoppingListRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class FakeShoppingListRepository: ShoppingListRepository{

    private val shoppingItems = mutableListOf<ShoppingListDto>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingListDto>>(shoppingItems)

    private var shouldReturnNetworkError = false

    fun shouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    override suspend fun getShoppingListItems(): Response<ShoppingListDto> {
        return if (shouldReturnNetworkError)
            Response.error(403, ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                "{\"key\":[\"Network Error\"]}")
            )
        else
            Response.success(ShoppingListDto(listOf(Product("url", "name", "2000", 2))))
    }
}