package com.timilehinaregbesola.lazerpay.data

import com.timilehinaregbesola.lazerpay.model.LazerPayData

interface LazerPayRepository {
    fun buildHtml(params: LazerPayData)
}
