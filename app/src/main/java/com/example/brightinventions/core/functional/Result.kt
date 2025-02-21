package com.example.brightinventions.core.functional

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T > {
    data class Success<out T >(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


fun <V> value(value: V): Result<V> =
    Result.Success(value)
fun error(exception: Exception): Result<Nothing> =
    Result.Error(exception)



