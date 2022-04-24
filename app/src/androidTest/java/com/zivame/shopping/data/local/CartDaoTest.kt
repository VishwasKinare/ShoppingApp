package com.zivame.shopping.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.zivame.shopping.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CartDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var shoppingDatabase: ShoppingDatabase
    private lateinit var cartDao: CartDao

    @Before
    fun setup(){
        shoppingDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDatabase::class.java
        ).allowMainThreadQueries().build()
        cartDao = shoppingDatabase.getCartDao()
    }

    @After
    fun teardown(){
        shoppingDatabase.close()
    }

    @Test
    fun insertCartItem() = runBlockingTest {
        val cartItem = CartItem(id = 0, name = "name", img = "url", quantity = 1, price = "34000", timeStamp = System.currentTimeMillis())
        cartDao.upsert(cartItem)

        val allCartItems = cartDao.getAllCartItems().getOrAwaitValue()

        assertThat(allCartItems).contains(cartItem)
    }

    @Test
    fun deleteCartItem() = runBlockingTest {
        val cartItem = CartItem(id = 0, name = "name", img = "url", quantity = 1, price = "34000", timeStamp = System.currentTimeMillis())
        cartDao.upsert(cartItem)
        cartDao.delete(cartItem)

        val allCartItems = cartDao.getAllCartItems().getOrAwaitValue()

        assertThat(allCartItems).doesNotContain(cartItem)
    }

    @Test
    fun observeCount() = runBlockingTest {
        val cartItem1 = CartItem(id = 1, name = "name1", img = "url1", quantity = 1, price = "34001", timeStamp = System.currentTimeMillis())
        val cartItem2 = CartItem(id = 2, name = "name2", img = "url2", quantity = 2, price = "34002", timeStamp = System.currentTimeMillis())
        val cartItem3 = CartItem(id = 3, name = "name3", img = "url3", quantity = 3, price = "34003", timeStamp = System.currentTimeMillis())

        cartDao.upsert(cartItem1)
        cartDao.upsert(cartItem2)
        cartDao.upsert(cartItem3)

        val count = cartDao.getCount().getOrAwaitValue()

        assertThat(count).isEqualTo(3)
    }

    @Test
    fun updateCartItem() = runBlockingTest {
        val cartItem = CartItem(id = 0, name = "name", img = "url", quantity = 1, price = "34000", timeStamp = System.currentTimeMillis())
        cartDao.upsert(cartItem)
        cartItem.apply {
            this.quantity = 2
        }
        cartDao.upsert(cartItem)

        val itemsCount = cartDao.getCount().getOrAwaitValue()

        assertThat(itemsCount).isEqualTo(1)
    }
}