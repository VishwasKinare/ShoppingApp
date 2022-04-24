package com.zivame.shopping.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.zivame.shopping.data.repository.FakeShoppingListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class GetShoppingListUseCaseTest{

    private lateinit var getShoppingListUseCase: GetShoppingListUseCase
    private lateinit var fakeRepository: FakeShoppingListRepository

    @Before
    fun setup(){
        fakeRepository = FakeShoppingListRepository()
        getShoppingListUseCase = GetShoppingListUseCase(fakeRepository)
    }

    @Test
    fun getShoppingListItemsSuccess(){
        runBlockingTest {
            val repo = fakeRepository.getShoppingListItems()
            assertThat(repo.isSuccessful).isTrue()
        }
    }

    @Test
    fun getShoppingListItemsFail(){
        fakeRepository.shouldReturnNetworkError(true)
        runBlockingTest {
            val repo = fakeRepository.getShoppingListItems()
            assertThat(repo.isSuccessful).isFalse()
        }
    }
}