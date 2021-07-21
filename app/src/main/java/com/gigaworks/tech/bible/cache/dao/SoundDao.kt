package com.gigaworks.tech.bible.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gigaworks.tech.bible.cache.entity.SoundEntity

@Dao
interface SoundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSounds(vararg sounds: SoundEntity)

    @Query("SELECT * FROM sounds WHERE page='audiobook'")
    suspend fun getAudioBooks(): List<SoundEntity>

    @Query("SELECT * FROM sounds WHERE page='daily' GROUP BY maincategory")
    suspend fun getDailyDevotionals(): List<SoundEntity>

    @Query("SELECT * from sounds WHERE maincategory=:mainCategory AND page='daily' AND category=:category")
    suspend fun getDailyDevotionalByCategory(mainCategory: String, category: String): List<SoundEntity>

    @Query("select * from sounds WHERE page='yeshu' OR page='dummy' GROUP BY maincategory")
    suspend fun getYeshuList(): List<SoundEntity>

    @Query("select * from sounds WHERE page='yeshu'")
    suspend fun getYeshuItemList(): List<SoundEntity>
}