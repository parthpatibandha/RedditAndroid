package com.patibandha.movieapp.presentation.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.patibandha.movieapp.data.contract.HomeRepo
import com.patibandha.movieapp.data.models.RedditPopularItemListPRQ
import com.patibandha.movieapp.data.models.RedditPopularItemListRS
import com.patibandha.movieapp.data.models.RedditPopularItem
import com.patibandha.movieapp.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

class MovieListingViewModel constructor(private val homeRepo: HomeRepo) : BaseViewModel() {

    val movieListRSLiveData: MutableLiveData<RedditPopularItemListRS> = MutableLiveData()
    val redditPopularItemListLocalLiveData: MutableLiveData<List<RedditPopularItem>> = MutableLiveData()
    val insertMovieListLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var favouriteMovieList : List<RedditPopularItem> = arrayListOf()

    fun getMovieList(page : String, after : String? = "") {
        launch {
            postValue(homeRepo.getAllMovieList(RedditPopularItemListPRQ(page, after)), movieListRSLiveData)
        }
    }

    fun insertMovieList(list: List<RedditPopularItem>) {
        launch {
            postValue(homeRepo.insertMovieList(list), insertMovieListLiveData)
        }
    }

    fun insertMovie(item: RedditPopularItem) {
        launch {
            postValue(homeRepo.insertMovie(item), insertMovieListLiveData)
        }
    }

    fun getMovieListLocal() {
        launch {
            postValue(homeRepo.getAllMovieListLocal(), redditPopularItemListLocalLiveData)
        }
    }

    fun getMovieListFavouriteLocal() : LiveData<List<RedditPopularItem>> {
        return homeRepo.getMovieListFavouriteLocal()
    }

}