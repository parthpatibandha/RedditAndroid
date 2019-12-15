package com.patibandha.movieapp.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.patibandha.movieapp.data.models.RedditPopularItem

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(list: List<RedditPopularItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(item: RedditPopularItem)

    @Query("SELECT * FROM redditPopularItem")
    fun getAllMovieList(): List<RedditPopularItem>

    @Query("SELECT * FROM redditPopularItem WHERE isSelected = 1")
    fun getAllFavouriteMovieList(): LiveData<List<RedditPopularItem>>

}