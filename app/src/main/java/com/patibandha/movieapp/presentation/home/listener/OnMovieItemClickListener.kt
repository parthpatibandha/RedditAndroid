package com.patibandha.movieapp.presentation.home.listener

import com.patibandha.movieapp.data.models.RedditPopularItem

interface OnMovieItemClickListener {
    fun onMovieItemClick(redditPopularItem : RedditPopularItem)
    fun onMovieItemFavouriteClick(redditPopularItem : RedditPopularItem)
    fun onMovieItemShareClick(redditPopularItem : RedditPopularItem)
}