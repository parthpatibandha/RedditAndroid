package com.patibandha.movieapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.patibandha.movieapp.data.models.RedditPopularItem
import com.patibandha.movieapp.presentation.home.MovieListingActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieListingActivityInstrumentedTest   {

    @Rule
    @JvmField
    public val rule = ActivityTestRule(MovieListingActivity::class.java)

    val redditPopularItemList : MutableList<RedditPopularItem>? = arrayListOf()
    val emptyList : MutableList<RedditPopularItem>? = arrayListOf()

    @Before
    fun beforeTest(){
        redditPopularItemList?.add(RedditPopularItem(0, "RedditPopularItem 0", "2019", "Action", "abc.png"))
        redditPopularItemList?.add(RedditPopularItem(1, "RedditPopularItem 1", "2018", "Drama", "abc_drama.png"))
    }

    @Test
    fun testNetworkConnectionAvailable(){
        Assert.assertTrue(isNetworkAvailable(rule.activity))
    }

    @Test
    fun testNetworkConnectionNotAvailable(){
        val wifi = rule.activity.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifi.isWifiEnabled = false
        Assert.assertNotEquals(false, isNetworkAvailable(rule.activity))
    }

    fun isNetworkAvailable(context : Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}