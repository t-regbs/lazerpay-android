package com.timilehinaregbesola.lazerpay.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class LazerPayResult : Parcelable {
    @Parcelize
    object Success : LazerPayResult()

    @Parcelize
    data class Error(val exception: Throwable) : LazerPayResult()

    @Parcelize
    object Close : LazerPayResult()

    @Parcelize
    object Initialize : LazerPayResult()
}
