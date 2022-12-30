package fr.danyhamimi.projet_hamimi_kaabeche

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// MainActivity.kt

class NavigatorActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var shareButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)

        webView = findViewById(R.id.web_view)
        shareButton = findViewById(R.id.share_button)

        val word = intent.getStringExtra("word2translate")
        var sourceLanguage =intent.getStringExtra("LangSource")
        var targetLanguage = intent.getStringExtra("Lang2Trad")
        val url = "https://www.wordreference.com/$sourceLanguage$targetLanguage/$word"
        webView.loadUrl(url)

        shareButton.setOnClickListener {
            shareWebPage()
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (Uri.parse(url).host == "www.wordreference.com") {
                    return false
                }
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                return true
            }
        }
    }

    private fun shareWebPage() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, webView.url)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Share website"))
    }
}
