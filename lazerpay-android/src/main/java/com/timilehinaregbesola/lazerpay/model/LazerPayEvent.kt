package com.timilehinaregbesola.lazerpay.model

internal enum class LazerPayEventType(val value: String) {
    close("ON_CLOSE"),
    fetch("ON_FETCH"),
    success("ON_SUCCESS"),
    copy("ON_COPY")
}

internal sealed class LazerPayEvent

internal class CloseEvent : LazerPayEvent()

internal class FetchEvent(val data: InitializeData) : LazerPayEvent()

internal class CopyEvent : LazerPayEvent()

internal class SuccessEvent(val data: SuccessData) : LazerPayEvent()
