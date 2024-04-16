package com.example.wishlist_demo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("wish-title")
    val title: String="",
    @ColumnInfo("wish-desc")
    val description: String=""
)

object dummyWish{
    val wishList = listOf(
        Wish(title = "hello", description = "blah.... blah...."),
        Wish(title = "hey", description = "blah.... blah...."),
        Wish(title = "Damn", description = "blah.... blah....")

    )
}
