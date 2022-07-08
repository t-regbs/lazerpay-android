package com.timilehinaregbesola.lazerpay.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

internal sealed class LazerPayResult : Parcelable {
    @Parcelize
    data class Success(val data: SuccessData) : LazerPayResult()

    @Parcelize
    data class Error(val exception: Throwable) : LazerPayResult()

    @Parcelize
    object Cancel : LazerPayResult()
}
