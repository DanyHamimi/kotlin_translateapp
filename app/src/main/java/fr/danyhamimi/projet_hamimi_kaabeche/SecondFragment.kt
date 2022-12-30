package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    fun getLink(word: String, sourceLanguage: String, targetLanguage: String): String {
        val url = "https://www.larousse.fr/dictionnaires/$sourceLanguage-$targetLanguage/$word"
        return url
    }


    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonSecond2.setOnClickListener{
            val db = Room.databaseBuilder(
                requireContext(),
                LanguageDatabase::class.java, "languageDB2"
            ).allowMainThreadQueries().build()

            db.languageDao.getLanguageById(1)

            val word = binding.editTextTextPersonName.text.toString()

            val language1 = binding.spinner.selectedItem.toString()

            val language2 = binding.spinner2.selectedItem.toString()
            var word2 : String?
            if(language1 == "Francais" && language2 == "Anglais"){
                var idFR = db.languageDao.getId(word)
                if (idFR != null)
                {
                    word2 = db.languageDao.getWordEN(idFR)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("fr","en",word)
                }
                binding.textView.text = word2.toString()
            }
            else if(language1 == "Francais" && language2 == "Espagnol"){
                var idFR = db.languageDao.getId(word)
                if (idFR != null)
                {
                    word2 = db.languageDao.getWordES(idFR)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("fr","es",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Francais" && language2 == "Allemand"){
                var idFR = db.languageDao.getId(word)
                if (idFR != null)
                {
                    word2 = db.languageDao.getWordDE(idFR)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("fr","de",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Anglais" && language2 == "Francais"){
                var idEN = db.languageDao.getId(word)
                if (idEN != null)
                {
                    word2 = db.languageDao.getWord(idEN)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("en","fr",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Anglais" && language2 == "Espagnol"){
                var idEN = db.languageDao.getId(word)
                if (idEN != null)
                {
                    word2 = db.languageDao.getWordES(idEN)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("en","es",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Anglais" && language2 == "Allemand"){
                var idEN = db.languageDao.getId(word)
                if (idEN != null)
                {
                    word2 = db.languageDao.getWordDE(idEN)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("en","de",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Espagnol" && language2 == "Francais"){
                var idES = db.languageDao.getId(word)
                if (idES != null)
                {
                    word2 = db.languageDao.getWord(idES)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("es","fr",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Espagnol" && language2 == "Anglais"){
                var idES = db.languageDao.getId(word)
                if (idES != null)
                {
                    word2 = db.languageDao.getWordEN(idES)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("es","en",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Espagnol" && language2 == "Allemand"){
                var idES = db.languageDao.getId(word)
                if (idES != null)
                {
                    word2 = db.languageDao.getWordDE(idES)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("es","de",word)
                }
                binding.textView.text = word.toString()
            }

            else if(language1 == "Allemand" && language2 == "Francais"){
                var idDE = db.languageDao.getId(word)
                if (idDE != null)
                {
                    word2 = db.languageDao.getWord(idDE)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("de","fr",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Allemand" && language2 == "Anglais"){
                var idDE = db.languageDao.getId(word)
                if (idDE != null)
                {
                    word2 = db.languageDao.getWordEN(idDE)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("de","en",word)
                }
                binding.textView.text = word2.toString()
            }

            else if(language1 == "Allemand" && language2 == "Espagnol"){
                var idDE = db.languageDao.getId(word)
                if (idDE != null)
                {
                    word2 = db.languageDao.getWordES(idDE)
                }
                else
                {
                    word2 = "Pas de traduction"
                    translatePage("de","es",word)
                }
                binding.textView.text = word2.toString()
            }

                    
            
            
        }
    }
    private fun translatePage(sourceL: String, destL: String, word: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Aucune traduction")
        builder.setMessage("Aucun traduction n'a été trouvée voulez vous en chercher une en ligne ?")

        builder.setPositiveButton("Oui") { _, _ ->
            val intent = Intent(requireContext(), NavigatorActivity::class.java)
            intent.putExtra("word2translate",word)
            intent.putExtra("LangSource",sourceL)
            intent.putExtra("Lang2Trad",destL)
            startActivity(intent)
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Ajouter le mot à la base de donnée")
            builder.setMessage("Voulez vous ajouter le mot à la base de donnée ?")

            builder.setPositiveButton("Oui") { _, _ ->
            }

            builder.setNegativeButton("Non") { _, _ ->
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        builder.setNegativeButton("Non") { _, _ ->
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}