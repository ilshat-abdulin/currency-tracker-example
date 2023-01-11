package com.air.coins.di

import androidx.lifecycle.ViewModel
import com.air.core.di.CoreComponent

class CoinsComponentHolder : ViewModel() {

    private var component: CoinsComponent? = null

    fun getComponent(coreComponent: CoreComponent): CoinsComponent {
        if (component == null) {
            component = DaggerCoinsComponent.factory().create(coreComponent)
        }
        return component as CoinsComponent
    }
}