package com.rajkumar.shadidotcom.repository

import androidx.lifecycle.LiveData

import retrofit2.http.GET
import retrofit2.http.Query


interface IWebserviceInterface {


    @GET(".")
    fun getProfile(
        @Query("results") number: String?
    ): LiveData<ApiCallBack<ProfileResponse?>>

}