package com.example.movieapp.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Loading<T>() : Resource<T>()
    class Error<T>(data: T? = null, message: String?) : Resource<T>(data = data, message = message)
}
