package com.patibandha.movieapp.domain

import com.patibandha.movieapp.data.models.RedditPopularItemListRS
import com.patibandha.movieapp.presentation.utility.ApiConstant
import retrofit2.http.*

interface MovieApiService {

    @Headers("Content-Type: application/json")
    @GET(ApiConstant.API_MOVIES)
    suspend fun getAllMovieList(
        @Query("limit") limit : String,
        @Query("after") after : String? = ""
    ): RedditPopularItemListRS
}