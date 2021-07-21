package com.gigaworks.tech.bible.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gigaworks.tech.bible.cache.dao.BookDao
import com.gigaworks.tech.bible.cache.dao.SoundDao
import com.gigaworks.tech.bible.cache.entity.BookEntity
import com.gigaworks.tech.bible.cache.entity.SoundEntity

@Database(entities = [BookEntity::class, SoundEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun soundDao(): SoundDao

}