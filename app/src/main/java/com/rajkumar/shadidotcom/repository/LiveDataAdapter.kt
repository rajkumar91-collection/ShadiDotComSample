package com.rajkumar.shadidotcom.repository

import androidx.lifecycle.LiveData
import retrofit2.Call

import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class LiveDataAdapter<T>(private val responseType: Type) :
    CallAdapter<T, LiveData<ApiCallBack<T>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): LiveData<ApiCallBack<T>> {
        return object : LiveData<ApiCallBack<T>>(){
            override fun onActive() {
                super.onActive()
                val apiResponse =
                    ApiCallBack<T>()
                if(!call.isExecuted)
                {
                    call.enqueue(object : Callback<T>{
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            postValue(apiResponse.create(t));
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(apiResponse.create(response));
                        }

                    })
                }

            }
        }
    }

}