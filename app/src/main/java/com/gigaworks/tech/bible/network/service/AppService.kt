package com.gigaworks.tech.bible.network.service

import com.gigaworks.tech.bible.domain.Book
import com.gigaworks.tech.bible.domain.Sound
import retrofit2.http.GET

interface AppService {

    @GET("getBooks.php")
    suspend fun getBooks(): List<Book>

    @GET("getSound.php")
    suspend fun getSound(): List<Sound>

}