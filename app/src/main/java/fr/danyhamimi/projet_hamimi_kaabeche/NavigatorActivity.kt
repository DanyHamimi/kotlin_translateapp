package fr.danyhamimi.projet_hamimi_kaabeche

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import java.io.File


class NavigatorActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var shareButton: Button
    private lateinit var urlSend: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)

        webView = findViewById(R.id.web_view)
        shareButton = findViewById(R.id.share_button)

        val word = intent.getStringExtra("word2translate")
        var sourceLanguage =intent.getStringExtra("LangSource")
        var targetLanguage = intent.getStringExtra("Lang2Trad")
        var webSite = intent.getStringExtra("webSite")
        if(word != null){
            var url =""
            if(webSite =="Recherche google" || webSite == "Reverso"){
                var langue1 =""
                var langue2 =""
                if(sourceLanguage == "fr"){
                    langue1 = "french"
                }
                else if (sourceLanguage == "es"){
                    langue1 = "spanish"
                }
                else if (sourceLanguage == "de"){
                    langue1 = "german"
                }
                else if (sourceLanguage == "en"){
                    langue1 = "english"
                }
                if(targetLanguage == "fr"){
                    langue2 = "french"
                }
                else if (targetLanguage == "es"){
                    langue2 = "spanish"
                }
                else if (targetLanguage == "de"){
                    langue2 = "german"
                }
                else if (targetLanguage == "en") {
                    langue2 = "english"
                }
                if(webSite == "Recherche google"){
                    url = "https://www.google.com/search?q=$word+translation+$langue1+to+$langue2"
                    urlSend = "https://www.google.com/search?q="
                }
                else{
                    url = "https://dictionnaire.reverso.net/$langue1-$langue2/$word"
                    urlSend = "https://dictionnaire.reverso.net/"
                }
            }
            else if(webSite =="Reverso"){

            }
            else if(webSite =="Wordreference"){
                url = "https://www.wordreference.com/$sourceLanguage$targetLanguage/$word"
                urlSend = "https://www.wordreference.com/"
            }
            webView.loadUrl(url)

            shareButton.setOnClickListener {
                shareWebPage()
            }

            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (Uri.parse(url).host == "www.wordreference.com" || Uri.parse(url).host == "www.google.com") {
                        // This is my web site, so do not override; let my WebView load the page
                        return false
                    }
                    // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    return true
                }
            }
        }
        else{
            var url = "https://www.wordreference.com/"
            shareButton.visibility = View.GONE

            if(intent.getStringExtra("UrlSaved") != null)
            {
                url = intent.getStringExtra("UrlSaved")!!
                webView.loadUrl(url)
            }
            else if(intent.getStringExtra("UrlLocation") != null){
                webView.getSettings().allowContentAccess = true;
                webView.getSettings().allowFileAccess = true;
                var b = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
                val file = File(b, intent.getStringExtra("UrlLocation"))
                val fileUrl = file.toURI().toURL().toString()
                webView.loadUrl(fileUrl)
            }
        }

    }

    private fun shareWebPage() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, webView.url)
            putExtra("word2translate",intent.getStringExtra("word2translate"))
            putExtra("Lang2Trad",intent.getStringExtra("Lang2Trad"))
            putExtra("LangSource",intent.getStringExtra("LangSource"))
            putExtra("LinkName",intent.getStringExtra("webSite")) // TODO DO IT FOR ALL DICTIONARY
            putExtra("Link",urlSend)

            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Share website"))
        finish()
    }
}
