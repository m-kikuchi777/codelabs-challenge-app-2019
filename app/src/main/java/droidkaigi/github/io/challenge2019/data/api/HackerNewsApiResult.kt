package droidkaigi.github.io.challenge2019.data.api

sealed class HackerNewsApiResult {
    data class Success<T>(val data: T) : HackerNewsApiResult()
    data class Failed(val throwable: Throwable) : HackerNewsApiResult()
}