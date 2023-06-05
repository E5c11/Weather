package com.demo.weather.common.helper

import com.demo.weather.common.helper.Constant.LOADING
import com.demo.weather.common.io.ActionableException

data class Resource<out T>(val status: Status, val data: T? = null, val error: ActionableException?, val loading: String = LOADING) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: ActionableException? = null, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        fun <T> loading(loading: String = LOADING, data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, loading)
        }
    }
}

fun <T> Resource<T>.isLoading(): Boolean {
    return this.status == Resource.Status.LOADING
}

fun <T> Resource<T>.isError(): Boolean {
    return this.status == Resource.Status.ERROR
}

fun <T> Resource<T>.isSuccess(): Boolean {
    return this.status == Resource.Status.SUCCESS
}