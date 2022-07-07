package com.timilehinaregbesola.lazerpay.model

enum class LazerPayEventType(val value: String) {
    close("ON_CLOSE"),
    fetch("ON_FETCH"),
    success("ON_SUCCESS"),
    copy("ON_COPY")
}

sealed class LazerPayEvent

class CloseEvent : LazerPayEvent()

class FetchEvent(val data: InitializeData) : LazerPayEvent()

class CopyEvent : LazerPayEvent()

class SuccessEvent(val data: SuccessData) : LazerPayEvent()
