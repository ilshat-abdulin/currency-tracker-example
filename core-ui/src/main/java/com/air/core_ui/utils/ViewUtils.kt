package com.air.core_ui.utils

import android.view.View
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val DEBOUNCE: Long = 300L
private var isClickEnabled: Boolean = true
private val enable: () -> Unit = { isClickEnabled = true }

fun View.onClickWithDebounce(action: () -> Unit) {
    this.setOnClickListener { v ->
        if (isClickEnabled) {
            isClickEnabled = false
            v.postDelayed(enable, DEBOUNCE)
            action.invoke()
        }
    }
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