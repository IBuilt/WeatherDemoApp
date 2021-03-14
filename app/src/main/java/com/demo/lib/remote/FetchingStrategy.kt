package com.demo.lib.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlin.coroutines.CoroutineContext


fun <A> fetchAndSaveOperation(
    context: CoroutineContext,
    networkCall: suspend () -> FetchedResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<FetchedResult<A>> =

    liveData(context) {

        emit(FetchedResult.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == FetchedResult.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            emit(FetchedResult.success(responseStatus.data))

        } else if (responseStatus.status == FetchedResult.Status.ERROR) {
            emit(FetchedResult.error(responseStatus.message!!))
        }
    }