package com.gigaworks.tech.bible.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gigaworks.tech.bible.cache.dao.BookDao
import com.gigaworks.tech.bible.cache.dao.SoundDao
import com.gigaworks.tech.bible.cache.entity.BookEntity
import com.gigaworks.tech.bible.cache.entity.SoundEntity
import com.gigaworks.tech.bible.domain.toEntity
import com.gigaworks.tech.bible.network.safeApiCall
import com.gigaworks.tech.bible.network.service.AppService
import com.gigaworks.tech.bible.util.Result
import com.gigaworks.tech.bible.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val network: AppService,
    private val soundDao: SoundDao,
    private val bookDao: BookDao
) {

    suspend fun getBookBySort(sort: String): Result<List<BookEntity>> {
        return withContext(IO) {
            try {
                Result.Success(bookDao.getBooksBySort(sort))
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getBookBySort() : ${e.message}"
                )
                Result.Failure(false, null, "error getting book data")
            }
        }
    }

    suspend fun getAudioBooks(): List<SoundEntity> {
        return withContext(IO) {
            try {
                soundDao.getAudioBooks()
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getAudioBooks() : ${e.message}"
                )
                listOf()
            }
        }
    }

    suspend fun getDailyDevotional(): List<SoundEntity> {
        return withContext(IO) {
            try {
                soundDao.getDailyDevotionals()
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getDailyDevotional() : ${e.message}"
                )
                listOf()
            }
        }
    }

    suspend fun getYeshuList(): List<SoundEntity> {
        return withContext(IO) {
            try {
                soundDao.getYeshuList()
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getYeshuList() : ${e.message}"
                )
                listOf()
            }
        }
    }

    suspend fun getYeshuItemList(): List<SoundEntity> {
        return withContext(IO) {
            try {
                soundDao.getYeshuItemList()
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getYeshuList() : ${e.message}"
                )
                listOf()
            }
        }
    }

    suspend fun getDailyDevotionalByCategory(mainCategory: String, category: String): List<SoundEntity> {
        return withContext(IO) {
            try {
                soundDao.getDailyDevotionalByCategory(mainCategory, category)
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getDailyDevotional() : ${e.message}"
                )
                listOf()
            }
        }
    }

    suspend fun getAllBooks(): List<BookEntity> {
        return withContext(IO) {
            try {
                bookDao.getAllBooks()
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getAllBooks() : ${e.message}"
                )
                listOf()
            }
        }
    }

    suspend fun getBooksByCategory(category: String): List<BookEntity> {
        return withContext(IO) {
            try {
                bookDao.getBooksByCategory(category)
            } catch (e: Exception) {
                printLogD(
                    this.javaClass.simpleName,
                    "getBooksByCategory() : ${e.message}"
                )
                listOf()
            }
        }
    }

    suspend fun getAllBooksFromNetwork(): Boolean {
        return when (val response = safeApiCall { network.getBooks() }) {
            is Result.Failure -> {
                printLogD(
                    this.javaClass.simpleName,
                    "getAllBooksFromNetwork() : ${response.message}"
                )
                false
            }
            is Result.Success -> {
                val bookList = response.response.map { it.toEntity() }
                withContext(IO) {
                    bookDao.insertBooks(*bookList.toTypedArray())
                }
                true
            }
        }
    }

    suspend fun getAllSoundFromNetwork(): Boolean {
        return when (val response = safeApiCall { network.getSound() }) {
            is Result.Failure -> {
                printLogD(
                    this.javaClass.simpleName,
                    "getAllSoundFromNetwork() : ${response.message}"
                )
                false
            }
            is Result.Success -> {
                val soundList = response.response.map { it.toEntity() }
                withContext(IO) {
                    soundDao.insertSounds(*soundList.toTypedArray())
                }
                true
            }
        }
    }

}