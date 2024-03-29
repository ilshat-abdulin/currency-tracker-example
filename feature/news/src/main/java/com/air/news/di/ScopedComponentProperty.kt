package com.air.news.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Used from "https://proandroiddev.com/how-to-store-scoped-dagger-components-in-android-applications-4f3e86a5e957"
 */
private class ScopedComponentProperty<T>(
    private val storeOwner: ViewModelStoreOwner,
    private val componentProvider: () -> T
) : Lazy<T> {

    private var cached: T? = null

    override val value: T
        get() {
            val component = cached
            if (component != null) return component
            val viewModels = ViewModelProvider(storeOwner, object : ViewModelProvider.Factory {
                override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                    return ScopedComponentHolder(componentProvider()) as VM
                }
            })
            val componentHolder = viewModels[ScopedComponentHolder::class.java]
            return (componentHolder.component as T).also { cached = it }
        }

    override fun isInitialized() = cached != null
}

fun <T> ViewModelStoreOwner.scopedComponent(
    componentProvider: () -> T
): Lazy<T> {
    return ScopedComponentProperty(this, componentProvider)
}