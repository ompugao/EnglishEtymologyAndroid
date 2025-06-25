package com.ompugao.englishetymology

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings.LOAD_DEFAULT
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.ompugao.englishetymology.R.id
import com.ompugao.englishetymology.databinding.ActivityScrollingBinding


class ScrollingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        
        val webView = binding.webview
        val progressBar = binding.webViewProgressBar
        webView.webViewClient = object: WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.cacheMode = LOAD_DEFAULT

        val editor = binding.editWord
        val word_candidates = resources.getStringArray(R.array.word_candidates)
        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_dropdown_item_1line, word_candidates
        )
        editor.setAdapter(adapter)


        val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        if (!text.isNullOrEmpty()) {
            val textstr = text.toString()
            editor.setText(textstr)
            showWordOnWebView(textstr)
        }
        
        setupBackPressedCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val webView = binding.webview
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    @Suppress("UNUSED_PARAMETER")
    fun onSearchButtonPressed(view: View) {
        val text = binding.editWord.text.toString()
        showWordOnWebView(text)
    }

    private fun showWordOnWebView(text: String) {
        val webView = binding.webview
        webView.loadUrl("http://hidic.u-aizu.ac.jp/result.php?tableName=tango&word=" + Uri.encode(text))
        Log.d("ScrollingActivity", "showing word: $text")
    }
}
