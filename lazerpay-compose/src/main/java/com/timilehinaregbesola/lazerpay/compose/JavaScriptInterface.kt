package com.timilehinaregbesola.lazerpay.compose

import android.util.Log
import android.webkit.JavascriptInterface
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.timilehinaregbesola.lazerpay.common.model.CloseEvent
import com.timilehinaregbesola.lazerpay.common.model.CopyEvent
import com.timilehinaregbesola.lazerpay.common.model.FetchEvent
import com.timilehinaregbesola.lazerpay.common.model.LazerPayEvent
import com.timilehinaregbesola.lazerpay.common.model.LazerPayEventType
import com.timilehinaregbesola.lazerpay.common.model.SuccessEvent

class JavaScriptInterface(
    val onSucess: (SuccessData) -> Unit,
    val onError: (Exception) -> Unit,
    val onClose: () -> Unit,
    val onCopy: () -> Unit
) {
    @JavascriptInterface
    fun messageFromWeb(dataStr: String) {
        Log.i("Interface", dataStr)

        val eventAdapterFactory =
            PolymorphicJsonAdapterFactory.of(LazerPayEvent::class.java, "type")
                .withSubtype(CloseEvent::class.java, LazerPayEventType.close.value)
                .withSubtype(FetchEvent::class.java, LazerPayEventType.fetch.value)
                .withSubtype(SuccessEvent::class.java, LazerPayEventType.success.value)
                .withSubtype(CopyEvent::class.java, LazerPayEventType.copy.value)

        val eventAdapter = Moshi.Builder()
            .add(eventAdapterFactory)
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(LazerPayEvent::class.java)
        try {
            val event = eventAdapter.fromJson(dataStr)
            event?.let {
                when (it) {
                    is SuccessEvent -> {
                        onSucess(Mapper().mapFromCommonData(it.data))
                    }
                    is CloseEvent -> {
                        onClose()
                    }
                    is FetchEvent -> {
                    }
                    is CopyEvent -> {
                        onCopy()
                    }
                }
            }
        } catch (e: JsonDataException) {
            Log.e("Interface", e.message, e)
        }
    }

    @JavascriptInterface
    fun rawMessageFromWeb(dataStr: String) {
        Log.i("Interface", dataStr)
    }
}
