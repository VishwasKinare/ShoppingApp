package com.zivame.shopping.data.remote

import com.zivame.shopping.data.dto.ShoppingListDto
import retrofit2.Response
import retrofit2.http.GET

interface ShoppingApi {

    @GET("nancymadan/assignment/db")
    suspend fun getCoinList(): Response<ShoppingListDto>
}