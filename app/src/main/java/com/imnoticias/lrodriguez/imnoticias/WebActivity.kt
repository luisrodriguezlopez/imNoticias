package com.imnoticias.lrodriguez.imnoticias

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class WebActivity :  AppCompatActivity() {
    var mywebview: WebView? = null
    lateinit var webUrl : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_activity)
        mywebview = findViewById<WebView>(R.id.web_view_new)
        mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
                view?.loadUrl(getIntent().getStringExtra("webUrl"))
                return true
            }
        }
        print(getIntent().getStringExtra("webUrl"))
        mywebview!!.loadUrl(getIntent().getStringExtra("webUrl"))
    }


}