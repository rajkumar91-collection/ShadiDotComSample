package com.rajkumar.shadidotcom.repository

import com.rajkumar.altbalajitestapp.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class WebServiceController {




    companion object {



        private val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(
                Constant.CONNECTION_TIMEOUT,
                TimeUnit.SECONDS
            )
            .retryOnConnectionFailure(false)
            .build()


        private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .addCallAdapterFactory(LiveDataAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())

        private val retrofit = retrofitBuilder.build()

        private val apiService: IWebserviceInterface = retrofit.create(
            IWebserviceInterface::class.java)
        fun getApiService(): IWebserviceInterface? {
        return apiService
    }
}
}