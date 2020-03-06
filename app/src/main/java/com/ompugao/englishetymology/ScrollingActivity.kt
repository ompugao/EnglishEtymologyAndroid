package com.ompugao.englishetymology

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ompugao.englishetymology.R.id
import kotlinx.android.synthetic.main.activity_scrolling.*


class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        val webView = findViewById<WebView>(id.webview)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        if (!text.isNullOrEmpty()) {
            val textstr = text.toString()
            val editor = findViewById<EditText>(id.editWord)
            editor.setText(textstr)
            showWordOnWebView(textstr)
        }
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

    override fun onBackPressed() {
        val webView: WebView = findViewById(id.webview)
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    fun onSearchButtonPressed(view: View) {
        val editor = findViewById<EditText>(id.editWord)
        val text = editor.text.toString()
        showWordOnWebView(text)
    }

    fun showWordOnWebView(text: String) {
        val webView = findViewById<WebView>(id.webview)
        webView.loadUrl("http://hidic.u-aizu.ac.jp/result.php?tableName=tango&word="+ Uri.encode(text))
        Log.d("showing word: %s", text)
    }
}
