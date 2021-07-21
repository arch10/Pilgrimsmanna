package com.gigaworks.tech.bible.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gigaworks.tech.bible.domain.Sound

@Entity(tableName = "sounds")
data class SoundEntity(
    @ColumnInfo(name = "blocked") val blocked: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "category_sort") val categorySort: String?,
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "maincategory") val mainCategory: String?,
    @ColumnInfo(name = "page") val page: String?,
    @ColumnInfo(name = "sort") val sort: String?,
    @ColumnInfo(name = "soundfile") val soundFile: String?,
    @ColumnInfo(name = "title") val title: String
)

fun SoundEntity.toDomain(): Sound {
    return Sound(
        blocked,
        category,
        categorySort,
        id,
        image,
        mainCategory,
        page,
        sort,
        soundFile,
        title
    )
}
