package com.github.mag0716.locationpermissioninwebview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_LOCATION = 100
        private const val REQUEST_LOCATION_IN_WEBVIEW = 101
    }

    private var origin: String? = null
    private var callback: GeolocationPermissions.Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        }
        webView.settings.apply {
            javaScriptEnabled = true
            setGeolocationEnabled(true)
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                    origin: String?,
                    callback: GeolocationPermissions.Callback?) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                if (ActivityCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED) {
                    callback?.invoke(origin, true, false)
                } else {
                    callback?.invoke(origin, false, false)
                    this@MainActivity.origin = origin
                    this@MainActivity.callback = callback
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_IN_WEBVIEW)
                }
            }
        }

        webView.loadUrl("https://www.google.co.jp/maps/")
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION) {
            Toast.makeText(
                    this,
                    "onRequestPermissionsResult : ${permissions[0]} is ${grantResults[0]}",
                    Toast.LENGTH_SHORT)
                    .show()

        } else if (requestCode == REQUEST_LOCATION_IN_WEBVIEW) {
            callback?.invoke(origin, grantResults[0] == PackageManager.PERMISSION_GRANTED, false)
        }
    }


}
