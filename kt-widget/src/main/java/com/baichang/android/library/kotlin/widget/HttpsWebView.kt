package com.baichang.android.library.kotlin.widget

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.SslError
import android.util.AttributeSet
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by iCong on 05/01/2018.
 */
class HttpsWebView : WebView {

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(
    context: Context,
    attrs: AttributeSet
  ) : super(context, attrs) {
    init()
  }

  constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int
  ) : super(
      context, attrs,
      defStyleAttr
  ) {
    init()
  }

  @SuppressLint("SetJavaScriptEnabled") private fun init() {
    if (isInEditMode) {
      return
    }
    webViewClient = object : WebViewClient() {
      override fun onReceivedSslError(
        view: WebView,
        handler: SslErrorHandler,
        error: SslError
      ) {
        Log.d("HttpsWebView", error.toString())
        handler.proceed()
      }
    }
    settings.useWideViewPort = true
    settings.loadWithOverviewMode = true
    settings.javaScriptEnabled = true
  }
}
