package com.patibandha.movieapp.presentation.core

import android.arch.lifecycle.Observer
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.patibandha.movieapp.R
import com.patibandha.movieapp.data.repository.BaseRepository
import com.patibandha.movieapp.presentation.utility.Logger
import com.patibandha.movieapp.presentation.utility.showDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    abstract fun getViewModel(): BaseViewModel?

    private var job = Job()
    private var progressDialog: CustomProgressDialog? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        attachBaseObserver()
    }

    private fun attachBaseObserver() {
        getViewModel()?.exceptionLiveData?.observe(this, Observer {
            it?.apply {
                parseError(this)
            }
        })
    }

    private fun parseError(exception: Exception) {
        exception.printStackTrace()
        hideProgress()
        Logger.d("Exception >>> ${exception.message}")
        when (exception) {
            is BaseRepository.MyAppException.DataBaseInsertionError -> {
                showDialog(getString(R.string.app_name), getString(R.string.error_database),
                        getString(R.string.ok), DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                return
            }
            is BaseRepository.MyAppException.UnparcebleEntityException -> {
                showError(getString(R.string.error_json))
                return
            }
            is BaseRepository.MyAppException.ServerException -> {
                showError(getString(R.string.error_server))
                return
            }
            is BaseRepository.MyAppException.InternetException -> {
                showError(getString(R.string.error_data_connection))
                return
            }
        }
        showError(getString(R.string.error_something))
    }

    private fun showError(msg: String) {
        showDialog(getString(R.string.app_name), msg,
                getString(R.string.ok), DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    private fun init() {
        job = Job()
        createDialog()
    }

    private fun createDialog() {
        progressDialog = CustomProgressDialog(this)
    }

    fun showProgress() {
        runOnUiThread {
            if (!this.isFinishing && progressDialog?.isShowing == false) {
                progressDialog?.show()
            }
        }
    }

    fun hideProgress() {
        runOnUiThread {
            if (progressDialog?.isShowing == true) {
                progressDialog?.cancel()
            }
        }
    }

    fun updateProgressMsg(msg: String) {
        runOnUiThread {
            progressDialog?.updateMsg(msg)
        }
    }

    fun setupToolbarBase(toolBar: Toolbar) {
        setSupportActionBar(toolBar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun addFragment(id: Int, fragment: Fragment, addToBackStack: Boolean? = false) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(id, fragment)
        if (addToBackStack == true) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun replaceFragment(id: Int, fragment: Fragment, addToBackStack: Boolean? = false) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(id, fragment)
        transaction.commitAllowingStateLoss()
    }


}