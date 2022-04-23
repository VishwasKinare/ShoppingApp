package com.zivame.shopping.di

import android.content.Context
import androidx.room.Room
import com.zivame.shopping.core.Constants.SHOPPING_DATABASE_NAME
import com.zivame.shopping.data.local.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideShoppingDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ShoppingDatabase::class.java,
        SHOPPING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: ShoppingDatabase) = db.getCartDao()
}