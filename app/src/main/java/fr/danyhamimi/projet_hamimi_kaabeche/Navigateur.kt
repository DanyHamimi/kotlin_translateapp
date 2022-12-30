package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.FragmentFirstBinding
//import kotlinx.android.synthetic.main.fragment_web_view.*

class Navigateur : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    fun loadWebPage(url: String) {
        val webView = WebView(this.requireContext())
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "https://www.larousse.fr"
        this.loadWebPage(url)
    }
}