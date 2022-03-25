package com.air.crypto.presentation

import android.app.Application
import com.air.crypto.di.DaggerApplicationComponent

class CryptoApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}