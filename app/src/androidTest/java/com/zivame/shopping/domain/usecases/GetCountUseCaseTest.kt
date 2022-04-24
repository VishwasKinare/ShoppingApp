package com.zivame.shopping.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.data.repository.FakeCartRepository
import com.zivame.shopping.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCountUseCaseTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getCountUseCase: GetCountUseCase
    private lateinit var fakeRepository: FakeCartRepository

    @Before
    fun setup(){
        fakeRepository = FakeCartRepository()
        getCountUseCase = GetCountUseCase(fakeRepository)

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
    fun countItemsInCart() {
        val count = getCountUseCase().getOrAwaitValue()
        Truth.assertThat(count).isEqualTo(26)
    }
}