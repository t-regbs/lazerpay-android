package com.timilehinaregbesola.lazerpay.compose

sealed class LoadingState {
    object Initializing : LoadingState()
    object Loading : LoadingState()
    object Finished : LoadingState()
}
