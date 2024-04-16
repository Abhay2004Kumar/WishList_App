package com.example.wishlist_demo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  fun addWish(wish: Wish)

    @Query("SELECT*FROM `wish-table`")
    abstract  fun getAllWishes(): Flow<List<Wish>>

    @Update
    abstract suspend fun updateAWish(wishEntry: Wish)  //suspend function used for parallel and background

    @Delete
    abstract suspend fun deleteAWish(wishEntry: Wish)

    @Query("SELECT*FROM `wish-table` WHERE id= :id")
    abstract fun getAWishById(id: Long): Flow<Wish>







}