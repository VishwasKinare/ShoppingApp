package com.zivame.shopping.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String = "",
    var img: String = "",
    var quantity: Int = 0,
    var price: String = "",
    var timeStamp: Long = 0L,
)