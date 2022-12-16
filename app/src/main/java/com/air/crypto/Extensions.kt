package com.air.crypto

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.bumptech.glide.Glide
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.disk)
        .into(this)
}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val searchQuery = MutableStateFlow("")
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            searchQuery.value = newText
            return true
        }
    })
    return searchQuery
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