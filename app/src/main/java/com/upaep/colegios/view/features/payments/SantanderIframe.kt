package com.upaep.colegios.view.features.payments

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Preview
@Composable
fun SantanderIframeScreen() {
    val iframeUrl = "https://www.google.com/"

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
            }
        },
        update = { webView ->
            webView.loadUrl(iframeUrl)
        }
    )
}
