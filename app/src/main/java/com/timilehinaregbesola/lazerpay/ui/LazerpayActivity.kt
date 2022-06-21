package com.timilehinaregbesola.lazerpay.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.JavaScriptReplyProxy
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewCompat.WebMessageListener
import androidx.webkit.WebViewFeature
import com.timilehinaregbesola.lazerpay.databinding.ActivityLazerpayBinding
import com.timilehinaregbesola.lazerpay.model.LazerPayCurrency
import com.timilehinaregbesola.lazerpay.model.LazerPayData
import com.timilehinaregbesola.lazerpay.model.LazerPayHtml

class LazerpayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLazerpayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                listenToWebEvents(view!!)
//                webView.isVisible = true
            }
        }
        val data = LazerPayData(
            publicKey = "pk_test_LIfI1h8BvlW25UMxGQQCzgSula1MnrdVY7T5TcbOEKIh5uue36",
            name = "Regbs",
            email = "regbs@gmail.com",
            amount = "500",
            businessLogo = "https://securecdn.pymnts.com/wp-content/uploads/2021/12/stablecoins.jpg",
            currency = LazerPayCurrency.NGN,
            reference = "W6b8hV55l0435t354544"
        )
        webView.loadData(LazerPayHtml().buildLazerPayHtml(data), "text/html", "base64")
    }

    @SuppressLint("RequiresFeature")
    private fun listenToWebEvents(webView: WebView) {
        val myListener: WebMessageListener = object : WebMessageListener {
            override fun onPostMessage(
                view: WebView,
                message: WebMessageCompat,
                sourceOrigin: Uri,
                isMainFrame: Boolean,
                replyProxy: JavaScriptReplyProxy
            ) {
                val dataStr = message.data ?: return
                Log.i(TAG, dataStr)
                handleCheckoutResponse(dataStr)
//                replyProxy.postMessage("Got it!")
            }
        }
        WebViewCompat.addWebMessageListener(webView, JS_OBJECT, mutableSetOf("*"), myListener)
    }

    private fun handleCheckoutResponse(dataStr: String) {
    }

    companion object {
        private const val TAG = "LazerpayActivity"
        private const val JS_OBJECT = "lazerpayClient"
    }
}
