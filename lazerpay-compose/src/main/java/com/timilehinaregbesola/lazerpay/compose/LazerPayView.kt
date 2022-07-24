package com.timilehinaregbesola.lazerpay.compose

import android.annotation.SuppressLint
import android.graphics.Bitmap
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.timilehinaregbesola.lazerpay.common.model.LazerPayHtml
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LazerPayView(
    modifier: Modifier = Modifier,
    data: LazerPayParams,
    onSucess: (SuccessData) -> Unit,
    onError: (Exception) -> Unit,
    onClose: () -> Unit
) {
    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(scaffoldState = state) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var loadingState: LoadingState by remember { mutableStateOf(LoadingState.Initializing) }
            var webView by remember { mutableStateOf<WebView?>(null) }
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
                            JavaScriptInterface(onSucess, onError, onClose) {
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
                    }.also { webView = it }
                },
                modifier = modifier
            ) { view ->
                view.loadData(uri, "text/html", "base64")
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
