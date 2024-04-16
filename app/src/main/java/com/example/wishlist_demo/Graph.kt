package com.example.wishlist_demo

import android.content.Context
import androidx.room.Room
//here object declares singleton
object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()

    }
}