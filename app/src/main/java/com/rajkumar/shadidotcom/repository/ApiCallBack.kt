package com.rajkumar.shadidotcom.repository

import retrofit2.Response
import java.io.IOException

open class ApiCallBack<T> {
    fun create(error: Throwable): ApiCallBack<T> {
        return ApiErrorCallBack(if (error.message == "") error.message else "Please check network connection")
    }

    fun create(response: Response<T>): ApiCallBack<T> {
        return if (response.isSuccessful) {
            val body = response.body()

            if (body == null || response.code() == 204) { // 204 is empty response
                ApiEmptyCallBack()
            } else {
                ApiSuccessCallBack(body)
            }
        } else {
            val errorMsg: String?
            errorMsg = try {
                response.errorBody()!!.string()
            } catch (e: IOException) {
                e.printStackTrace()
                response.message()
            }
            ApiErrorCallBack(errorMsg)
        }
    }


    inner class ApiSuccessCallBack<T> internal constructor(val body: T) : ApiCallBack<T>()


    inner class ApiErrorCallBack<T> internal constructor(val errorMessage: String?) :
        ApiCallBack<T>()

    inner class ApiEmptyCallBack<T> : ApiCallBack<T>()
}