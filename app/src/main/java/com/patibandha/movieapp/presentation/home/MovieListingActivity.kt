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

class MovieListingActivity : BaseActivity(), OnMovieItemClickListener {

    private val movieListingViewModel: MovieListingViewModel by viewModel()

    override fun getViewModel() = movieListingViewModel

    private lateinit var movieListAdapter: MovieListAdapter

    private var limit : Int = 20
    private var page: Int = 1
    private var after : String = ""
    private var isLoading = false
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_listing)

        init()
    }

    private fun init() {
        setupToolbarBase(toolbar)
        attachObserver()
        setupAdapter()
        getMovieList()
    }

    private fun attachObserver() {

        movieListingViewModel.getMovieListFavouriteLocal().observe(this, Observer {
            it?.apply {
                movieListingViewModel.favouriteMovieList = this

                movieListAdapter.updateDataSet(movieListingViewModel.favouriteMovieList)
            }
        })

        movieListingViewModel.movieListRSLiveData.observe(this, Observer {
            it?.apply {

                this@MovieListingActivity.after = this.redditPopularItemChildren?.after.orEmpty()

                if(this.redditPopularItemChildren?.items?.isNotEmpty() == true){
                    val movieList : MutableList<RedditPopularItem> = arrayListOf()
                    this.redditPopularItemChildren?.items?.forEachIndexed { index, redditPopularItemData ->

                        movieListingViewModel.favouriteMovieList.find {
                            it.id.contentEquals(redditPopularItemData.item?.id.orEmpty())
                        }?.apply {
                            redditPopularItemData.item?.isSelected = this.isSelected
                        }
                        redditPopularItemData.item?.imageUrl = redditPopularItemData.item?.preview?.prviewImages?.get(0)?.source?.url.orEmpty()
                        redditPopularItemData.item?.apply {
                            movieList.add(this)
                        }
                    }
                    movieListingViewModel.insertMovieList(movieList)
                    if(page == 1){
                        updateDataset(movieList)
                    } else {
                        movieListAdapter.addData(movieList)
                    }

                } else {
                    if(page == 1){
                        tvNoDataFound.visible()
                        recyclerView.gone()
                    }
                }
                hideProgress()
                isLoading = false
            }
        })

        movieListingViewModel.redditPopularItemListLocalLiveData.observe(this, Observer {
            it?.apply {
                updateDataset(this)
                hideProgress()
                isLoading = false
            }
        })
    }

    private fun getMovieList() {
        showProgress()
        isLoading = true
        if (isNetworkAvailable()) {
            if(after.isNotEmpty()){
                movieListingViewModel.getMovieList(limit.toString(), after)
            } else {
                movieListingViewModel.getMovieList(limit.toString())
            }
        } else {
            if(movieListAdapter.list.isEmpty()) {
                movieListingViewModel.getMovieListLocal()
            } else{
                hideProgress()
            }
        }
    }

    private fun setupAdapter() {
        movieListAdapter = MovieListAdapter(arrayListOf(), this)
        recyclerView.adapter = movieListAdapter
        recyclerView.itemAnimator = FlexibleItemAnimator()
        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(current_page: Int) {
                if (!isLoading && !isSearching) {
                    page++
                    getMovieList()
                }
            }
        })
    }

    private fun updateDataset(list: List<RedditPopularItem>?) {
        if (list?.isEmpty() == true) {
            tvNoDataFound.visible()
            recyclerView.gone()
        } else {
            tvNoDataFound.gone()
            recyclerView.visible()
            movieListAdapter.setData(list.orEmpty())
        }
    }

    //#region - listeners

    override fun onMovieItemClick(redditPopularItem: RedditPopularItem) {
        startActivity(Intent(this, MovieDetailActivity::class.java).also {
            val bundle = Bundle()
            bundle.putParcelable(RedditPopularItem::class.java.simpleName, redditPopularItem)
            it.putExtras(bundle)
        })
    }

    override fun onMovieItemFavouriteClick(redditPopularItem: RedditPopularItem) {
        movieListingViewModel.insertMovie(redditPopularItem)
    }

    override fun onMovieItemShareClick(redditPopularItem: RedditPopularItem) {
        var message = redditPopularItem.title.orEmpty()
        message = message.plus("\n").plus("Url : ").plus(redditPopularItem.description)
        shareContent(message, redditPopularItem.imageUrl.orEmpty())
    }

    //endregion

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.menuFav -> {
                startActivity(Intent(this, MovieListingFavouriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
