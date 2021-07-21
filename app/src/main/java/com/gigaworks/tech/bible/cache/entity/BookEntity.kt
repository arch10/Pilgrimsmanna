package com.gigaworks.tech.bible.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gigaworks.tech.bible.domain.Book

@Entity(tableName = "books")
data class BookEntity(
    @ColumnInfo(name = "blocked") val blocked: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "chapter") val chapter: String,
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "sort") val sort: String,
    @ColumnInfo(name = "soundfile") val soundFile: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "title") val title: String
)

fun BookEntity.toDomain(): Book {
    return Book(
        blocked,
        category,
        chapter,
        id,
        sort,
        soundFile,
        text,
        title
    )
}