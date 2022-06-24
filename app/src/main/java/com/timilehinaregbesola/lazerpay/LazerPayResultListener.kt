package com.timilehinaregbesola.lazerpay

import com.timilehinaregbesola.lazerpay.model.SuccessData

interface LazerPayResultListener {
    fun onSuccess(result: SuccessData)

    fun onError(exception: Throwable)

    fun onCancelled()
}
