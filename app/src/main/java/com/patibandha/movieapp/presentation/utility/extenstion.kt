package com.patibandha.movieapp.presentation.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.patibandha.movieapp.R

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.loadImage(url: String, placeHolder: Int? = R.mipmap.ic_launcher) {
    GlideApp.with(this)
        .load(url)
        .centerCrop()
        .placeholder(placeHolder!!)
        .error(placeHolder)
        .into(this)
}

fun Context.showDialog(
    title: String, message: String,
    positiveButtonText: String? = getString(R.string.ok),
    positiveListener: DialogInterface.OnClickListener? = null,
    negativeButtonText: String? = getString(R.string.cancel),
    negativeListener: DialogInterface.OnClickListener? = null
) {
    val builder = AlertDialog.Builder(this, R.style.ThemeDialogShowDialog)
    builder.setTitle(title)
    builder.setMessage(message)
    if (positiveButtonText?.isNotEmpty() == true && positiveListener != null) {
        builder.setPositiveButton(positiveButtonText) { dialog, which ->
            positiveListener.onClick(dialog, which)
        }
    }
    if (negativeButtonText?.isNotEmpty() == true && negativeListener != null) {
        builder.setNegativeButton(negativeButtonText) { dialog, which ->
            negativeListener.onClick(dialog, which)
        }
    }
    val dialog = builder.create()
    dialog.show()
}

fun Activity.showToast(strMessage: String) {
    Toast.makeText(this, strMessage, Toast.LENGTH_LONG).show()
}

fun Activity.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}

fun TextView.getHtmlString(htmlData : String){
    if (Build.VERSION.SDK_INT >= 24) {
        this.text = Html.fromHtml(htmlData, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(htmlData)
    }
}

fun Context.shareContent(message : String, uriToImage : String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
      //  putExtra(Intent.EXTRA_STREAM, uriToImage)
      //  type = "image/jpeg"
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}