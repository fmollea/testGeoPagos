package com.mollea.testgeopagos.extensions

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mollea.testgeopagos.R

@BindingAdapter("loadUrl")
fun ImageView.bindLoadUrl(url: String) {
    Glide.with(this.context)
        .applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.mipmap.ic_default)
                .error(R.mipmap.ic_default))
        .load(url)
        .into(this)
}