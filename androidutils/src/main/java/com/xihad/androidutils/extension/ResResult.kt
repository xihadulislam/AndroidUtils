package com.xihad.androidutils.extension

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResResult<out R> {

    data class Success<out T>(val data: T) : ResResult<T>()
    data class Error(val exception: Exception, val code: String = "-1") : ResResult<Nothing>()
    object Loading : ResResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[code=$code exception=${exception.message}]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [ResResult] is of type [Success] & holds non-null [Success.data].
 */
val ResResult<*>.succeeded
    get() = this is ResResult.Success && data != null
