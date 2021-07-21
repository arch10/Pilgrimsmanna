package com.gigaworks.tech.bible.domain


import android.os.Parcelable
import com.gigaworks.tech.bible.cache.entity.SoundEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sound(
    @SerializedName("blocked") val blocked: String,
    @SerializedName("category") val category: String,
    @SerializedName("category_sort") val categorySort: String?,
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String?,
    @SerializedName("maincategory") val mainCategory: String?,
    @SerializedName("page") val page: String?,
    @SerializedName("sort") val sort: String?,
    @SerializedName("soundfile") val soundFile: String?,
    @SerializedName("title") val title: String
) : Parcelable

fun Sound.toEntity(): SoundEntity {
    return SoundEntity(
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