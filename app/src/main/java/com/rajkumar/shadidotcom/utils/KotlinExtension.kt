package com.rajkumar.shadidotcom.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rajkumar.shadidotcom.R

 fun ImageView.loadImage(uri: String) {
    val options: RequestOptions = RequestOptions()
        .placeholder(R.drawable.no_pic)
        .error(R.drawable.no_pic)
    Glide.with(this.context)
        .setDefaultRequestOptions(options).load(uri).into(this)
}