package com.mutondo.weathermviapp.ui.mvibase

interface MviView<S : MviViewState> {
    fun render(state: S)
}