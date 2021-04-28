package com.rajkumar.shadidotcom.repository


open class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val status: Status? = null


) {
    class Success<T>(message: String, data: T) : Resource<T>(data, message,
        Status.SUCCESS
    )


    class Loading<T>(message: String, data: T? = null) : Resource<T>(data, message,
        Status.PROGRESS
    )

    class Error<T>(message: String,  data: T? = null) : Resource<T>(data, message,
        Status.ERROR
    )
}

enum class Status {
    PROGRESS,
    ERROR,
    SUCCESS
}