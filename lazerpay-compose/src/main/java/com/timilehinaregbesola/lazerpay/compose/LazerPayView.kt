package com.timilehinaregbesola.lazerpay.compose

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Base64
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.timilehinaregbesola.lazerpay.common.exception.MissingWebViewException
import com.timilehinaregbesola.lazerpay.common.exception.UnsupportedWebChannelException
import com.timilehinaregbesola.lazerpay.common.model.LazerPayHtml
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LazerPayView(
    modifier: Modifier = Modifier,
    data: LazerPayParams,
    onSuccess: (SuccessData) -> Unit,
    onError: (Exception) -> Unit,
    onClose: () -> Unit
) {
    if (WebViewCompat.getCurrentWebViewPackage(LocalContext.current) == null) {
        onError(MissingWebViewException())
    }
    if (!WebViewFeature.isFeatureSupported(WebViewFeature.CREATE_WEB_MESSAGE_CHANNEL)) {
        onError(UnsupportedWebChannelException())
    }
    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(scaffoldState = state) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var loadingState: LoadingState by remember { mutableStateOf(LoadingState.Initializing) }
            var uri by remember { mutableStateOf(LazerPayHtml().buildLazerPayHtml(Mapper().mapToCommonLazerPayData(data))) }
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        settings.apply {
                            javaScriptEnabled = true
                            javaScriptCanOpenWindowsAutomatically = true
                            domStorageEnabled = true
                        }
                        addJavascriptInterface(
                            JavaScriptInterface(onSuccess, onError, onClose) {
                                scope.launch {
                                    state.snackbarHostState.showSnackbar(
                                        message = "Copied!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            "lazerpayClient"
                        )
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                loadingState = LoadingState.Loading
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                loadingState = LoadingState.Finished
                            }
                        }
                    }
                },
                modifier = modifier
            ) { view ->
//                Log.d("LazerpayView", uri)
                view.loadData(
                    Base64.encodeToString(uri.toByteArray(), Base64.NO_PADDING),
                    "text/html",
                    "base64"
                )
            }
            if (loadingState is LoadingState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    color = Color(0xFF00666FB),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}
