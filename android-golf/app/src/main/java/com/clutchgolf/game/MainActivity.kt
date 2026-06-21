package com.clutchgolf.game

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView

/**
 * A tiny full-screen wrapper around the offline Clutch Golf game.
 *
 * The whole game is a single self-contained HTML file (copied into assets as
 * index.html at build time). It runs entirely in the WebView with no network
 * access. We expose a small AndroidApp bridge so the page can report which
 * screen it is on, letting the hardware Back button return to the clubhouse
 * before exiting the app.
 */
class MainActivity : Activity() {

    private lateinit var web: WebView
    @Volatile private var currentScene = "boot"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep the screen awake during a round.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        web = WebView(this)
        web.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true                    // localStorage for save data
            mediaPlaybackRequiresUserGesture = false
            allowFileAccess = true
            cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
        }
        web.setBackgroundColor(0xFF07261C.toInt())
        web.addJavascriptInterface(AppBridge(), "AndroidApp")
        web.loadUrl("file:///android_asset/index.html")

        setContentView(web)
        goImmersive()
    }

    /** Hide the status & navigation bars for a full-screen game feel. */
    @Suppress("DEPRECATION")
    private fun goImmersive() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) goImmersive()
    }

    /** Back button: return to the clubhouse first; only exit when already there. */
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (currentScene == "home" || currentScene == "boot") {
            super.onBackPressed()
        } else {
            runOnUiThread { web.evaluateJavascript("window.__goHome && window.__goHome();", null) }
        }
    }

    override fun onDestroy() {
        web.destroy()
        super.onDestroy()
    }

    /** Exposed to the page as window.AndroidApp */
    inner class AppBridge {
        @JavascriptInterface
        fun onScene(name: String) { currentScene = name }
    }
}
