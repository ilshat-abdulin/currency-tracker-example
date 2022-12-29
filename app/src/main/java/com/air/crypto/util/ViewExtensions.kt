package com.air.crypto.util

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.air.crypto.R
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.disk)
        .into(this)
}

fun SearchView.getQueryAsFlow(): Flow<String> {
    return callbackFlow {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                close()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                trySend(newText)
                return true
            }
        })
    }
}