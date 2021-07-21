package com.gigaworks.tech.bible.network

import com.gigaworks.tech.bible.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(apiCall.invoke())
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    Result.Failure(false, e.code(), e.message)
                }
                else -> {
                    Result.Failure(true, null, e.message)
                }
            }
        }
    }
}