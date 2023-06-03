package com.demo.weather.helpers

import com.demo.weather.helpers.Constant.LOADING

data class Resource<out T>(val status: Status, val data: T? = null, val error: Throwable?, val loading: String = LOADING) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Other<T> {
            return Other(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable? = null, data: T? = null): Other<T> {
            return Other(Status.ERROR, data, error)
        }

        fun <T> loading(loading: String = LOADING, data: T? = null): Other<T> {
            return Other(Status.LOADING, data, null, loading)
        }
    }
}

fun <T> Other<T>.isLoading(): Boolean {
    return this.status == Other.Status.LOADING
}

fun <T> Other<T>.isError(): Boolean {
    return this.status == Other.Status.ERROR
}

fun <T> Other<T>.isSuccess(): Boolean {
    return this.status == Other.Status.SUCCESS
}