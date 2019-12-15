package com.patibandha.movieapp.data.repository

import android.arch.lifecycle.LiveData
import com.patibandha.movieapp.data.Either
import com.patibandha.movieapp.data.contract.HomeRepo
import com.patibandha.movieapp.data.database.MoviesDao
import com.patibandha.movieapp.data.models.RedditPopularItem
import com.patibandha.movieapp.data.models.RedditPopularItemListPRQ
import com.patibandha.movieapp.data.models.RedditPopularItemListRS
import com.patibandha.movieapp.domain.MovieApiService

class HomeRepository constructor(
    private val moviesDao: MoviesDao,
    private val movieApiService: MovieApiService
) : BaseRepository(), HomeRepo {


    override suspend fun getAllMovieList(redditPopularItemListPRQ: RedditPopularItemListPRQ): Either<MyAppException, RedditPopularItemListRS> {
        return either {
            movieApiService.getAllMovieList(
                redditPopularItemListPRQ.limit,
                redditPopularItemListPRQ.after
            )
        }
    }

    override suspend fun insertMovieList(list: List<RedditPopularItem>): Either<MyAppException, Boolean> {
        return try {
            moviesDao.insertMovieList(list)
            Either.Right(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(MyAppException.DataBaseInsertionError(e))
        }
    }

    override suspend fun insertMovie(item: RedditPopularItem): Either<MyAppException, Boolean> {
        return try {
            moviesDao.insertMovie(item)
            Either.Right(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(MyAppException.DataBaseInsertionError(e))
        }
    }

    override suspend fun getAllMovieListLocal(): Either<MyAppException, List<RedditPopularItem>> {
        return try {
            Either.Right(moviesDao.getAllMovieList())
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(MyAppException.DataBaseInsertionError(e))
        }
    }

    override fun getMovieListFavouriteLocal(): LiveData<List<RedditPopularItem>> {
        return moviesDao.getAllFavouriteMovieList()
    }
}