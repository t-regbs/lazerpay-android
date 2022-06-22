package com.timilehinaregbesola.lazerpay.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LazerPayEvent(
    val type: EventType,
    val data: String?
)

enum class EventType { ON_SUCCESS, ON_CLOSE, ON_FETCH, ON_COPY }
