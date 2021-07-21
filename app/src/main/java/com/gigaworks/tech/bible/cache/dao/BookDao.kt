package com.gigaworks.tech.bible.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gigaworks.tech.bible.cache.entity.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(vararg books: BookEntity)

    @Query("SELECT * FROM books WHERE sort=:sort ORDER BY sort asc")
    suspend fun getBooksBySort(sort: String): List<BookEntity>

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM books WHERE category=:category")
    suspend fun getBooksByCategory(category: String): List<BookEntity>

}