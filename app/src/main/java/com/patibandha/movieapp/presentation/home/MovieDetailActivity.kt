package com.patibandha.movieapp.presentation.home

import android.os.Bundle
import com.patibandha.movieapp.R
import com.patibandha.movieapp.data.models.RedditPopularItem
import com.patibandha.movieapp.presentation.core.BaseActivity
import com.patibandha.movieapp.presentation.utility.loadImage
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.MenuItem
import com.patibandha.movieapp.presentation.utility.getHtmlString


class MovieDetailActivity : BaseActivity() {

    private val movieListingViewModel: MovieListingViewModel by viewModel()

    override fun getViewModel() = movieListingViewModel

    private var redditPopularItem: RedditPopularItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        init()
    }

    private fun init() {

        setupToolbarBase(toolbar)

        if (intent.extras != null && intent.extras?.containsKey(RedditPopularItem::class.java.simpleName) == true) {
            redditPopularItem = intent.extras?.getParcelable(RedditPopularItem::class.java.simpleName) as RedditPopularItem

            redditPopularItem?.apply {
                toolbarImage.loadImage(this.imageUrl.orEmpty())
                tvTitle.text = "Title: ".plus(this.title)
                tvDate.text = "Date: ".plus(this.createdUtc)
                tvSubTitle.getHtmlString(this.description.orEmpty())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        return if (id == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)

    }

}
