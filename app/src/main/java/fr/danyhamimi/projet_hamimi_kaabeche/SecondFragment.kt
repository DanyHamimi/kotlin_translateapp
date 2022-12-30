package fr.danyhamimi.projet_hamimi_kaabeche

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {


    private var _binding: FragmentSecondBinding? = null
    /*private val CheckDataWorld : LanguageDatabase by lazy {
        Room.databaseBuilder(this,LanguageDatabase::class.java,"CheckDataWorld")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }*/

    // This property is only valid between onCreateView and
    // onDestroyView.
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
            //Get Database from Room MainActivity
            val db = Room.databaseBuilder(
                requireContext(),
                LanguageDatabase::class.java, "languageDB2"
            ).allowMainThreadQueries().build()

            db.languageDao.getLanguageById(1)

            //Get value from editText
            val word = binding.editTextTextPersonName.text.toString()
            //Check if the word is in the database

            //Get the value of the first spinner
            val language1 = binding.spinner.selectedItem.toString()

            //Get the value of the second spinner
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
                }
                binding.textView.text = word2.toString()
            }
            else if(language1 == "Anglais" && language2 == "Francais"){
                var idEN = db.languageDao.getIdEN(word)
                if (idEN != null)
                {
                    word2 = db.languageDao.getWord(idEN)
                }
                else
                {
                    word2 = "Pas de traduction"
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
                }
                binding.textView.text = word2.toString()
            }
            else if(language1 == "Espagnol" && language2 == "Francais"){
                var idES = db.languageDao.getIdES(word)
                if (idES != null)
                {
                    word2 = db.languageDao.getWord(idES)
                }
                else
                {
                    word2 = "Pas de traduction"
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
                }
                binding.textView.text = word2.toString()
            }
            else if(language1 == "Allemand" && language2 == "Francais"){
                var idDE = db.languageDao.getIdDE(word)
                if (idDE != null)
                {
                    word2 = db.languageDao.getWord(idDE)
                }
                else
                {
                    word2 = "Pas de traduction"
                }
                binding.textView.text = word2.toString()
            }
            
            
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}