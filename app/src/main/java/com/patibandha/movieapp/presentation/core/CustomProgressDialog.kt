package com.patibandha.movieapp.presentation.core

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Window
import com.patibandha.movieapp.R
import kotlinx.android.synthetic.main.dialog_progressbar.*

class CustomProgressDialog(context: Context) : Dialog(context, R.style.ProgressDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progressbar)
        setCancelable(false)

        progress.indeterminateDrawable?.setColorFilter(ContextCompat.getColor(context, R.color.white),
                android.graphics.PorterDuff.Mode.MULTIPLY)
    }

    fun updateMsg(msg: String) {
        tvProgressMsg.text = msg
    }
}