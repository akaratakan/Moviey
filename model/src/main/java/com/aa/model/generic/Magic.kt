package com.aa.model.generic

sealed class Magic<T> {
    data class Progress<T>(val isLoad: Boolean, val delayMillis: Long? = null) : Magic<T>()
    data class Success<T>(val data: T, val delay: Long? = null) : Magic<T>()
    data class Failure<T>(val errorMessage: String) : Magic<T>()

    companion object {
        fun <T> loading(isLoad: Boolean = true, delay: Long? = null): Magic<T> =
            Progress(isLoad, delay)

        fun <T> success(data: T, delay: Long? = null): Magic<T> = Success(data, delay)
        fun <T> failure(errorMessage: String): Magic<T> = Failure(errorMessage)
    }
}

fun <T, H> Magic<T>.mapData(block: (input: T) -> H): Magic<H> {
    return when (this) {
        is Magic.Success -> Magic.success(block.invoke(data), delay = 0L)
        is Magic.Failure -> Magic.failure(errorMessage)
        is Magic.Progress -> Magic.loading(isLoad, delayMillis)
    }
}