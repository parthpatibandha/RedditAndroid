package com.patibandha.movieapp.data.contract

import android.arch.lifecycle.LiveData
import com.patibandha.movieapp.data.Either
import com.patibandha.movieapp.data.models.RedditPopularItemListPRQ
import com.patibandha.movieapp.data.models.RedditPopularItemListRS
import com.patibandha.movieapp.data.models.RedditPopularItem
import com.patibandha.movieapp.data.repository.BaseRepository

interface HomeRepo {
    //remote
    suspend fun getAllMovieList(redditPopularItemListPRQ: RedditPopularItemListPRQ): Either<BaseRepository.MyAppException, RedditPopularItemListRS>

    //local
    suspend fun insertMovieList(list : List<RedditPopularItem>) : Either<BaseRepository.MyAppException, Boolean>
    suspend fun insertMovie(item : RedditPopularItem) : Either<BaseRepository.MyAppException, Boolean>
    suspend fun getAllMovieListLocal(): Either<BaseRepository.MyAppException, List<RedditPopularItem>>
    fun getMovieListFavouriteLocal(): LiveData<List<RedditPopularItem>>
}