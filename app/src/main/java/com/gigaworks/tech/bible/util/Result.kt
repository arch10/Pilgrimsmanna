package com.gigaworks.tech.bible.util

sealed class Result<out T> {
    data class Success<T>(val response: T) : Result<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val message: String?
    ) : Result<Nothing>()
}