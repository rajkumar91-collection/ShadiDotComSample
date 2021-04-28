package com.rajkumar.shadidotcom.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

abstract class NetworkBoundResource<CacheObject, RequestObject>(private val appExecutors: AppExecutors?) {
    private val results: MediatorLiveData<Resource<CacheObject?>?> = MediatorLiveData()
    private fun init() {

        results.setValue(
            Resource.Loading(
                "Loading...",
                null
            )
        )
        val dbSource = loadFromDb()
        results.addSource(dbSource){ cacheObject ->
            results.removeSource(dbSource)
            if (shouldFetch(cacheObject)) {
                fetchFromNetwork(dbSource)
            } else {
                results.addSource(dbSource) {
                    setValue(
                        Resource.Success(
                            "Success",
                            it
                        )
                    )

                }
            }
        }



    }

    private fun fetchFromNetwork(dbSource: LiveData<CacheObject?>) {


        results.addSource(dbSource) {
            setValue(Resource.Loading("Loading...", it))

        }
        val apiResponse: LiveData<ApiCallBack<RequestObject?>> = createCall()
        results.addSource(apiResponse, Observer { api ->

            results.removeSource(dbSource)
                results.removeSource(apiResponse)


            when (api) {
                is ApiCallBack<*>.ApiSuccessCallBack<*> -> {
                    AppExecutors.diskIO()?.execute(Runnable {
                        saveCallResult(processResponse(api as ApiCallBack<RequestObject>.ApiSuccessCallBack<RequestObject>))
                        AppExecutors.mainThread().execute(Runnable {
                            results.addSource(loadFromDb()) {

                                setValue(
                                    Resource.Success(
                                        "Success",
                                        it
                                    )
                                )

                            }
                        })
                    })
                }
                is ApiCallBack<*>.ApiEmptyCallBack<*> -> {
                    Log.d(
                        TAG,
                        "onChanged: ApiEmptyResponse"
                    )
                    AppExecutors.mainThread()?.execute(Runnable {
                        results.addSource(loadFromDb(), Observer {

                            setValue(
                                Resource.Success(
                                    "Success",
                                    it
                                )
                            )

                        })
                    })
                }
                is ApiCallBack<*>.ApiErrorCallBack<*> -> {

                    results.addSource(dbSource, Observer {

                        setValue(
                           /* Resource.error(
                                (requestObjectApiResponse as ApiResponse.ApiErrorResponse).getErrorMessage(),
                                cacheObject
                            )*/

                            Resource.Error(
                                api.errorMessage ?: "Success", it
                            )
                        )


                    })
                }
            }

        })
    }

    private fun processResponse(response: ApiCallBack<RequestObject>.ApiSuccessCallBack<RequestObject>): RequestObject? {
        return response.body
    }

    private fun setValue(newValue: Resource<CacheObject?>?) {
        if (results.value !== newValue) {
            results.value = newValue
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract fun saveCallResult(@NonNull response: RequestObject?)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: CacheObject?): Boolean

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): LiveData<CacheObject?>

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract fun createCall(): LiveData<ApiCallBack<RequestObject?>>

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    val asLiveData: LiveData<Resource<CacheObject?>?>?
        get() = results

    companion object {
        private val TAG: String? = "NetworkBoundResource"
    }

    init {
        init()
    }
}