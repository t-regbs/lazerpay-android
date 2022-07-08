package com.timilehinaregbesola.lazerpay.ui

import androidx.lifecycle.ViewModel
import com.timilehinaregbesola.lazerpay.model.LazerPayResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class LazerpayViewModel : ViewModel() {
    private val _lazerEventState = MutableStateFlow<LazerPayResult>(LazerPayResult.Cancel)
    val lazerEventState: StateFlow<LazerPayResult>
        get() = _lazerEventState

    fun updateState(newState: LazerPayResult) {
        _lazerEventState.value = newState
    }
}
