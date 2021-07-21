package com.gigaworks.tech.bible.domain

import android.os.Parcelable
import com.gigaworks.tech.bible.cache.entity.BookEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    @SerializedName("blocked") val blocked: String,
    @SerializedName("category") val category: String,
    @SerializedName("chapter") val chapter: String,
    @SerializedName("id") val id: String,
    @SerializedName("sort") val sort: String,
    @SerializedName("soundfile") val soundFile: String,
    @SerializedName("text") val text: String,
    @SerializedName("title") val title: String
) : Parcelable

fun Book.toEntity(): BookEntity {
    return BookEntity(
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