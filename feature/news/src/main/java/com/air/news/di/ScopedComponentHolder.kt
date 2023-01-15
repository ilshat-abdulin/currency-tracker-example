package com.air.news.di

import androidx.lifecycle.ViewModel

class ScopedComponentHolder<T>(
    val component: T
) : ViewModel()