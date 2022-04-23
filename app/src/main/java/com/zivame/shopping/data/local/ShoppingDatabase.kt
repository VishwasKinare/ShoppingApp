package com.zivame.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CartItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase: RoomDatabase() {
    abstract fun getCartDao(): CartDao
}