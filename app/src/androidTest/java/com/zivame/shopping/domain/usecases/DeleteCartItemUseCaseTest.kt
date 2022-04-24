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

class DeleteCartItemUseCaseTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var deleteCartItemUseCase: DeleteCartItemUseCase
    private lateinit var fakeRepository: FakeCartRepository
    private lateinit var item: CartItem

    @Before
    fun setup(){
        fakeRepository = FakeCartRepository()
        deleteCartItemUseCase = DeleteCartItemUseCase(fakeRepository)
        item = CartItem(
            id = 0,
            name = "name",
            img = "url",
            quantity = 1,
            price = "1000",
            timeStamp = System.currentTimeMillis()
        )
        runBlocking {
            fakeRepository.insertIntoCart(item)
        }
    }

    @Test
    fun deleteItemsFromCart() {
        runBlocking {
            deleteCartItemUseCase(item)
            Truth.assertThat(fakeRepository.getCount().getOrAwaitValue()).isEqualTo(0)
        }
    }
}