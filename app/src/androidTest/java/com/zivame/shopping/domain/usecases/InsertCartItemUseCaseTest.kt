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

class InsertCartItemUseCaseTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getInsertItemsUseCase: InsertCartItemUseCase
    private lateinit var fakeRepository: FakeCartRepository
    private lateinit var item: CartItem

    @Before
    fun setup(){
        fakeRepository = FakeCartRepository()
        getInsertItemsUseCase = InsertCartItemUseCase(fakeRepository)

        item = CartItem(
            id = 0,
            name = "name",
            img = "url",
            quantity = 1,
            price = "1000",
            timeStamp = System.currentTimeMillis()
        )
    }

    @Test
    fun insertItemsInCart() {
        runBlocking {
            getInsertItemsUseCase(item)
            Truth.assertThat(fakeRepository.getCount().getOrAwaitValue()).isEqualTo(1)
        }
    }
}