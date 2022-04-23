package com.zivame.shopping.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(run: CartItem)

    @Delete
    suspend fun delete(run: CartItem)

    @Query("SELECT * FROM cart_table ORDER BY timeStamp DESC")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Query("SELECT COUNT(id) FROM cart_table")
    fun getCount(): LiveData<Int>
}