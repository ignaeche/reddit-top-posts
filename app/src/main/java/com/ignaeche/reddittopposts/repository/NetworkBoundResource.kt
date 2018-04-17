package com.ignaeche.reddittopposts.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.ignaeche.reddittopposts.AppExecutors
import com.ignaeche.reddittopposts.api.ApiResponse

abstract class NetworkBoundResource<ResultType, RequestType>
    @MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.setValue(Resource.loading(null))
        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            // if shouldFetch is true, fetch from network otherwise return db data as successful
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> setValue(Resource.loading(newData)) }
        // attach apiResponse as source, save to db on saveCallResult if response is successful and
        // request data from db; otherwise call onFetchFailed, return error and data from db
        result.addSource<ApiResponse<RequestType>>(apiResponse) { response ->
            result.removeSource<ApiResponse<RequestType>>(apiResponse)
            result.removeSource(dbSource)

            if (response?.isSuccessful!!) {
                appExecutors.diskIO().execute({
                    saveCallResult(processResponse(response)!!)
                    appExecutors.mainThread().execute({
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb()) { newData -> setValue(Resource.success(newData)) }
                    })
                })
            } else {
                onFetchFailed()
                result.addSource(dbSource) { newData -> setValue(Resource.error(response.errorMessage!!, newData)) }
            }
        }

    }

    protected fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected fun processResponse(response: ApiResponse<RequestType>?): RequestType? {
        return response?.body
    }

    @WorkerThread
    protected abstract fun saveCallResult(request: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
