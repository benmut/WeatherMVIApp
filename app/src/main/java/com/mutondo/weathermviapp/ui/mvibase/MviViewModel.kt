package com.mutondo.weathermviapp.ui.mvibase

import androidx.lifecycle.LiveData

interface MviViewModel<I: MviIntent, S: MviViewState, R: MviResult> {
    fun processIntent(intent: I): LiveData<R>
    fun reduce(state: S, result: R): S
}