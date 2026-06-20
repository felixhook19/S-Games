package com.unicornmagic.app

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import java.util.Locale

/**
 * A tiny full-screen wrapper around the offline Unicorn Magic game.
 *
 * Why native plumbing at all? A bare Android WebView does not ship a working
 * Web Speech API, so the spoken numbers/colours/words would be silent. We
 * expose the device's own TextToSpeech engine to the page as `AndroidTTS`,
 * and the game uses it automatically when present. Everything runs offline.
 */
class MainActivity : Activity() {

    private lateinit var web: WebView
    private var tts: TextToSpeech? = null
    private var ttsReady = false
    @Volatile private var currentScene = "home"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep the screen awake during long car journeys.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Device text-to-speech, configured to sound bright and child-friendly.
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                try { tts?.language = Locale.UK } catch (e: Exception) {}
                tts?.setPitch(1.25f)
                tts?.setSpeechRate(0.9f)
                ttsReady = true
            }
        }

        web = WebView(this)
        web.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false   // let Web Audio start
            allowFileAccess = true
            cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
        }
        web.setBackgroundColor(0xFF2A1A5E.toInt())
        web.addJavascriptInterface(TtsBridge(), "AndroidTTS")
        web.addJavascriptInterface(AppBridge(), "AndroidApp")
        web.loadUrl("file:///android_asset/index.html")

        setContentView(web)
        goImmersive()
    }

    /** Hide the status & navigation bars (works on all supported versions). */
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

    /** Back button: return to the menu first; only exit when already there. */
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (currentScene == "home") {
            super.onBackPressed()
        } else {
            runOnUiThread { web.evaluateJavascript("window.__goHome && window.__goHome();", null) }
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        web.destroy()
        super.onDestroy()
    }

    /** Exposed to the page as window.AndroidTTS */
    inner class TtsBridge {
        @JavascriptInterface
        fun speak(text: String) {
            if (ttsReady && text.isNotBlank()) {
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "uni")
            }
        }
    }

    /** Exposed to the page as window.AndroidApp */
    inner class AppBridge {
        @JavascriptInterface
        fun onScene(name: String) { currentScene = name }
    }
}
