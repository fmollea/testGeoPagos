package com.mollea.testgeopagos.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mollea.testgeopagos.R

fun ImageView.loadUrlImage(url: String, context: Context) {
    Glide.with(context)
        .applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.mipmap.ic_default)
                .error(R.mipmap.ic_default))
        .load(url)
        .into(this)
}