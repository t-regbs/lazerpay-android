package com.timilehinaregbesola.lazerpay.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.timilehinaregbesola.lazerpay.databinding.ActivityLazerpayBinding
import com.timilehinaregbesola.lazerpay.exception.MissingWebViewException
import com.timilehinaregbesola.lazerpay.model.CloseEvent
import com.timilehinaregbesola.lazerpay.model.CopyEvent
import com.timilehinaregbesola.lazerpay.model.FetchEvent
import com.timilehinaregbesola.lazerpay.model.LazerPayData
import com.timilehinaregbesola.lazerpay.model.LazerPayEvent
import com.timilehinaregbesola.lazerpay.model.LazerPayEventType
import com.timilehinaregbesola.lazerpay.model.LazerPayHtml
import com.timilehinaregbesola.lazerpay.model.LazerPayResult
import com.timilehinaregbesola.lazerpay.model.SuccessEvent

class LazerpayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLazerpayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (WebViewCompat.getCurrentWebViewPackage(this) == null) {
            closeWithError(MissingWebViewException())
            return
        }

        binding = ActivityLazerpayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTransactionWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupTransactionWebView() {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.CREATE_WEB_MESSAGE_CHANNEL)) {
            // TODO: Finish with error result if web message channels aren't supported

            return
        }

        val webView = binding.webViewLzp
        webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
        }
        webView.addJavascriptInterface(JavaScriptInterface(), JS_OBJECT)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.pbQuote.visibility = View.GONE
            }
        }

        val data = intent.getParcelableExtra<LazerPayData>(EXTRA_DATA)
            ?: error("Lazerpay data not found")
        webView.loadData(LazerPayHtml().buildLazerPayHtml(data), "text/html", "base64")
    }

    private fun handleCheckoutResponse(event: LazerPayEvent) {
        when (event) {
            is SuccessEvent -> {
                val data = LazerPayResult.Success(event.data)
                closeWithResult(data)
            }
            is CloseEvent -> {
                closeWithResult(LazerPayResult.Close)
            }
            is FetchEvent -> {
//                closeWithResult(LazerPayResult.Initialize)
            }
            is CopyEvent -> {
                // Show snackbar or toast saying "Address copied"
            }
            else -> {}
        }
    }

    private fun closeWithResult(data: LazerPayResult) {
        val resultData = Intent()
        resultData.putExtra(EXTRA_TRANSACTION_RESULT, data)
        setResult(RESULT_OK, resultData)
        finish()
    }

    private fun closeWithError(exception: Throwable) {
        closeWithResult(LazerPayResult.Error(exception))
    }

    override fun onDestroy() {
        // Clean up
        binding.webViewLzp.removeJavascriptInterface(JS_OBJECT)
        super.onDestroy()
    }

    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun messageFromWeb(dataStr: String) {
            Log.i(TAG, dataStr)

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
                event?.let { handleCheckoutResponse(it) }
            } catch (e: JsonDataException) {
                Log.e(TAG, e.message, e)
            }
        }

        @JavascriptInterface
        fun rawMessageFromWeb(dataStr: String) {
            Log.i(TAG, dataStr)
        }
    }

    companion object {
        private const val TAG = "LazerpayActivity"
        private const val JS_OBJECT = "lazerpayClient"
        const val EXTRA_TRANSACTION_RESULT = "transaction_result"
        const val EXTRA_DATA = "lazerpay_data"
    }
}
