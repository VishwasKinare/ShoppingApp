package com.zivame.shopping.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.data.repository.FakeCartRepository
import com.zivame.shopping.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCartItemsUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getCartItemsUseCase: GetCartItemsUseCase
    private lateinit var fakeRepository: FakeCartRepository

    @Before
    fun setup(){
        fakeRepository = FakeCartRepository()
        getCartItemsUseCase = GetCartItemsUseCase(fakeRepository)

        val itemsToInsert = mutableListOf<CartItem>()
        ('a'..'z').forEachIndexed { index, i ->
            itemsToInsert.add(
                CartItem(
                    id = index,
                    name = i.toString(),
                    img = "url",
                    quantity = 1,
                    price = (index + 1000).toString(),
                    timeStamp = System.currentTimeMillis()
                )
            )
        }
        runBlocking {
            itemsToInsert.forEach{ fakeRepository.insertIntoCart(it) }
        }
    }

    @Test
    fun getItemsFromCartInOrder() {
        val cartItem = getCartItemsUseCase().getOrAwaitValue()

        for (i in 0..cartItem.size - 2){
            assertThat(cartItem[i].id).isLessThan(cartItem[i + 1].id)
        }
    }
}