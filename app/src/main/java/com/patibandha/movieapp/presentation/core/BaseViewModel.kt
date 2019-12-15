package com.patibandha.movieapp.presentation.core

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.patibandha.movieapp.data.Either
import com.patibandha.movieapp.data.repository.BaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    val exceptionLiveData: MutableLiveData<Exception> = MutableLiveData()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    fun <R> postValue(either: Either<BaseRepository.MyAppException, R>, response: MutableLiveData<R>) {
        either.either({
            exceptionLiveData.postValue(it)
        }, {
            response.postValue(it)
        })
    }
}