package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    lateinit var tmpLangu1 : String
    lateinit var tmpLangu2 : String
    lateinit var SpinnerValMem : String
    lateinit var Spinner2ValMem : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val db = Room.databaseBuilder(
            requireContext(),
            LanguageDatabase::class.java, "languageDB3"
        ).allowMainThreadQueries().build()

        val preferences = activity?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) ?: return

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.tradPage.visibility = View.GONE
        binding.deleteTrad.visibility = View.GONE
        val learningLanguage = preferences.getString("learning_language", "Anglais")
        val learningSourceLanguage = preferences.getString("learning_Sourcelanguage","Francais")

        // Définissez la sélection du Spinner en fonction de la valeur enregistrée dans les préférences
        val ArrayLanguage = resources.getStringArray(R.array.languages)
        binding.spinner2.setSelection(ArrayLanguage.indexOf(learningLanguage))
        binding.spinner.setSelection(ArrayLanguage.indexOf(learningSourceLanguage))
        SpinnerValMem = learningSourceLanguage.toString()
        Spinner2ValMem = learningLanguage.toString()
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent?.selectedItem.toString()
                if(selectedLanguage == SpinnerValMem){
                    //make a toast and say hello
                    val newPos = ArrayLanguage.indexOf(Spinner2ValMem)
                    binding.spinner.setSelection(newPos)
                    preferences.edit().putString("learning_Sourcelanguage", Spinner2ValMem).apply()

                }
                SpinnerValMem = binding.spinner.selectedItem.toString()
                Spinner2ValMem = binding.spinner2.selectedItem.toString()
                preferences.edit().putString("learning_language", selectedLanguage).apply()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent?.selectedItem.toString()
                if(selectedLanguage == Spinner2ValMem){
                    val newPos = ArrayLanguage.indexOf(SpinnerValMem)
                    binding.spinner2.setSelection(newPos)
                    preferences.edit().putString("learning_language", SpinnerValMem).apply()
                }
                SpinnerValMem = binding.spinner.selectedItem.toString()
                Spinner2ValMem = binding.spinner2.selectedItem.toString()
                preferences.edit().putString("learning_Sourcelanguage", selectedLanguage).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tradPage.visibility = View.GONE
                binding.deleteTrad.visibility = View.GONE
                binding.buttonSecond2.visibility = View.VISIBLE
                binding.textView.text = ""
                if(binding.editTextTextPersonName.text.trim().isEmpty() ||
                    binding.editTextTextPersonName.text.trim().startsWith(" ") ||
                    binding.editTextTextPersonName.text.trim().endsWith(" ")){ // To avoid spaces at begining or end
                    binding.buttonSecond2.visibility = View.GONE
                }
                else{
                    binding.buttonSecond2.visibility = View.VISIBLE
                }

                //check if the editText is empty or only spaces or start with spaces or end with spacesinding.buttonSecond2.visibility = View.VISIBLE
            }



            override fun afterTextChanged(s: Editable?) {
                // Action à effectuer chaque fois que le texte est modifié
            }
        })

        binding.buttonSecond2.setOnClickListener{
            //Get Database from Room MainActivity

            db.languageDao.getLanguageById(1)
            //Change Value of spinner2 to english
            //Get value from editText
            val word = binding.editTextTextPersonName.text.toString()

// Modification de la valeur sélectionnée du spinner

            //Check if the word is in the database

            //Get the value of the first spinner
            val language1 = binding.spinner.selectedItem.toString()

            //Get the value of the second spinner

            val language2 = binding.spinner2.selectedItem.toString()
            //Toast.makeText(requireContext(), word, Toast.LENGTH_SHORT).show()
            var word2 : String?
            //check if word is not empty
            if(word != null){
                if(language1 == "Francais"){
                    tmpLangu1 = "fr"
                    if(language2 == "Anglais"){
                        tmpLangu2 = "en"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"fr","en")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                            // Create text view to display url
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("fr","en",word)
                        }
                    }
                    else if(language2 == "Espagnol"){
                        tmpLangu2 = "es"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"fr","es")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("fr","es",word)
                        }
                    }
                    else if(language2 == "Allemand"){
                        tmpLangu2 = "de"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"fr","de")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("fr","de",word)
                        }
                    }
                    else{
                        word2 = "Veuillez changer la langue de destination"
                        HideVisibility()
                    }
                }
                else if(language1 == "Anglais"){
                    tmpLangu1 = "en"
                    if(language2 == "Francais"){
                        tmpLangu2 = "fr"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"en","fr")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("en","fr",word)
                        }
                    }
                    else if(language2 == "Espagnol"){
                        tmpLangu2 = "es"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"en","es")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("en","es",word)
                        }
                    }
                    else if(language2 == "Allemand"){
                        tmpLangu2 = "de"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"en","de")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("en","de",word)
                        }
                    }
                    else{
                        word2 = "Veuillez changer la langue de destination"
                        HideVisibility()
                    }
                }
                else if(language1 == "Espagnol"){
                    tmpLangu1 = "es"

                    if(language2 == "Francais"){
                        tmpLangu2 = "fr"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"es","fr")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("es","fr",word)
                        }
                    }
                    else if(language2 == "Anglais"){
                        tmpLangu2 = "en"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"es","en")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("es","en",word)
                        }
                    }
                    else if(language2 == "Allemand"){
                        tmpLangu2 = "de"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"es","de")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("es","de",word)
                        }
                    }
                    else{
                        word2 = "Veuillez changer la langue de destination"
                        HideVisibility()
                    }
                }
                else if(language1 == "Allemand"){
                    tmpLangu1 = "de"
                    if(language2 == "Francais"){
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"de","fr")
                        tmpLangu2 = "fr"
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("de","fr",word)
                        }
                    }
                    else if(language2 == "Anglais"){
                        tmpLangu2 = "en"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"de","en")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("de","en",word)
                        }
                    }
                    else if(language2 == "Espagnol"){
                        tmpLangu2 = "es"
                        val tmpValWord = db.languageDao.getLanguageByMotSource(word,"de","es")
                        if(tmpValWord != null){
                            word2 = tmpValWord.MotDestination
                            OpenPageFromDB(tmpValWord.Lien)
                        }
                        else{
                            word2 = "Pas de traduction"
                            HideVisibility()
                            translatePage("de","es",word)
                        }
                    }
                    else{
                        word2 = "Veuillez changer la langue de destination"
                        HideVisibility()
                    }
                }
                else{
                    word2 = "Veuillez changer la langue de destination"
                    HideVisibility()
                }
            }
            else{
                word2 = "Veuillez entrer un mot à traduire !!!"
                HideVisibility()
            }
            binding.textView.text = word2

        }
    }

    private fun HideVisibility(){
        binding.tradPage.visibility = View.GONE
        binding.deleteTrad.visibility = View.GONE
    }

    private fun OpenPageFromDB(lien: String) {
        binding.tradPage.visibility = View.VISIBLE
        binding.deleteTrad.visibility = View.VISIBLE
        binding.buttonSecond2.visibility = View.GONE
        binding.tradPage.setOnClickListener {
            //open activity navigator to the url
            val intent = Intent(requireContext(), NavigatorActivity::class.java)
            intent.putExtra("UrlSaved", lien)
            startActivity(intent)
        }
        binding.deleteTrad.setOnClickListener{
            val db = Room.databaseBuilder(
                requireContext(),
                LanguageDatabase::class.java, "languageDB3"
            ).allowMainThreadQueries().build()

            db.languageDao.deleteLanguageByMotSource(binding.editTextTextPersonName.text.toString(),tmpLangu1,tmpLangu2)
            db.languageDao.deleteLanguageByMotDestination(binding.editTextTextPersonName.text.toString(),tmpLangu2,tmpLangu1)
            binding.tradPage.visibility = View.GONE
            binding.deleteTrad.visibility = View.GONE
            binding.textView.setText("Traduction supprimée")
            binding.buttonSecond2.visibility = View.VISIBLE

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
            intent.putExtra("webSite",binding.dictionnaire.selectedItem.toString())
            startActivity(intent)
            //When goint back to the app, ask if the user want to add the word to the database
        }

        builder.setNegativeButton("Non") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}