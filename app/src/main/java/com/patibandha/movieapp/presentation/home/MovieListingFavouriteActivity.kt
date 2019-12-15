package com.patibandha.movieapp.presentation.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.patibandha.movieapp.R
import com.patibandha.movieapp.data.models.RedditPopularItem
import com.patibandha.movieapp.presentation.core.BaseActivity
import com.patibandha.movieapp.presentation.home.adapterItem.MovieListAdapter
import com.patibandha.movieapp.presentation.home.listener.OnMovieItemClickListener
import com.patibandha.movieapp.presentation.utility.*
import eu.davidea.flexibleadapter.common.FlexibleItemAnimator
import kotlinx.android.synthetic.main.activity_movie_listing.*
import kotlinx.android.synthetic.main.layout_empty.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListingFavouriteActivity : BaseActivity(), OnMovieItemClickListener {

    private val movieListingViewModel: MovieListingViewModel by viewModel()

    override fun getViewModel() = movieListingViewModel

    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_listing)

        init()
    }

    private fun init() {
        setupToolbarBase(toolbar)
        setupAdapter()
        getMovieList()
    }

    private fun getMovieList() {
        showProgress()
        movieListingViewModel.getMovieListFavouriteLocal().observe(this, Observer {
            it?.apply {
                setupAdapter()
                updateDataset(this)
                hideProgress()
            }
        })
    }

    private fun setupAdapter() {
        movieListAdapter = MovieListAdapter(arrayListOf(), this)
        recyclerView.adapter = movieListAdapter
        recyclerView.itemAnimator = FlexibleItemAnimator()
    }

    private fun updateDataset(list: List<RedditPopularItem>?) {
        if (list?.isEmpty() == true) {
            tvNoDataFound.visible()
            recyclerView.gone()
        } else {
            tvNoDataFound.gone()
            recyclerView.visible()
        }
        movieListAdapter.setData(list.orEmpty())
    }

    override fun onMovieItemClick(redditPopularItem: RedditPopularItem) {
        val bundle = Bundle()
        bundle.putParcelable(RedditPopularItem::class.java.simpleName, redditPopularItem)
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onMovieItemFavouriteClick(redditPopularItem: RedditPopularItem) {
        movieListingViewModel.insertMovie(redditPopularItem)
    }

    override fun onMovieItemShareClick(redditPopularItem: RedditPopularItem) {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
