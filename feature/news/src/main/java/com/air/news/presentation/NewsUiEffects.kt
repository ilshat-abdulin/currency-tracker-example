package com.air.news.presentation

import com.air.core_functional.Failure

sealed class NewsUiEffects {
    data class FailureEffect(val failure: Failure) : NewsUiEffects()
}
