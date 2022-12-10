package com.air.crypto

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.disk)
        .into(this)
}