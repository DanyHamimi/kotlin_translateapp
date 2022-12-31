package fr.danyhamimi.projet_hamimi_kaabeche

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.ActivityInGameBinding
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val PREFS_NAME = "app_prefs"
    lateinit var SpinnerValMem : String
    lateinit var Spinner2ValMem : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val errorMessageTextView = findViewById<TextView>(R.id.error_message)

        binding.cancelButton.setOnClickListener{
            finish()
        }
        binding.selectDictionaryButton.setOnClickListener{
            val intent = Intent(this, ManageDictionaryActivity::class.java)
            startActivity(intent)
        }
        binding.saveButton.setOnClickListener {
            if (binding.nameEditText.text.toString().isEmpty()) {
                errorMessageTextView.visibility = View.VISIBLE
                errorMessageTextView.text = "Veuillez entrer un prénom d'au moins 1 caractère"
            } else {
                errorMessageTextView.visibility = View.GONE
                val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("name", binding.nameEditText.text.toString())
                editor.putString(
                    "favorite_dictionary",
                    binding.favoriteDictionarySpinner.selectedItem.toString()
                )
                editor.putBoolean("save_pages_locally", binding.savePagesLocallyCheckBox.isChecked)
                editor.putInt("words_per_wave", binding.wordsPerWaveSeekBar.progress)
                editor.putString(
                    "notification_frequency",
                    binding.notificationFrequencySpinner.selectedItem.toString()
                )
                editor.apply()
                //TODO download all webpages from database

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val name = prefs.getString("name", "")
        val favoriteDictionary = prefs.getString("favorite_dictionary", "Wordreference")
        val savePagesLocally = prefs.getBoolean("save_pages_locally", true)
        val wordsPerWave = prefs.getInt("words_per_wave", 10)
        val notificationFrequencyAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.notification_frequencies,
            android.R.layout.simple_spinner_item
        )
        notificationFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.notificationFrequencySpinner.adapter = notificationFrequencyAdapter

        val notificationFrequency = prefs.getString("notification_frequency", "Tous les jours")

        val notificationFrequencyPosition = notificationFrequencyAdapter.getPosition(notificationFrequency)
        binding.notificationFrequencySpinner.setSelection(notificationFrequencyPosition)
        val learningLanguageSpinner: Spinner = findViewById(R.id.learning_language_spinner)
        val learningSourceLanguageSpinner: Spinner = findViewById(R.id.learningSource_language_spinner)

        val learningSourceLanguage = prefs.getString("learning_Sourcelanguage", "Anglais")
        val learningLanguage = prefs.getString("learning_language", "Francais")


        val ArrayLanguage = resources.getStringArray(R.array.languages)

        val position = ArrayLanguage.indexOf(learningSourceLanguage)
        learningSourceLanguageSpinner.setSelection(position)
        val position2 = ArrayLanguage.indexOf(learningLanguage)
        learningLanguageSpinner.setSelection(position2)

        SpinnerValMem = learningSourceLanguage.toString()
        Spinner2ValMem = learningLanguage.toString()


        binding.nameEditText.setText(name)
        val dictionaries = resources.getStringArray(R.array.dictionaries)
        val favoriteDictionaryIndex = dictionaries.indexOf(favoriteDictionary)
        binding.favoriteDictionarySpinner.setSelection(favoriteDictionaryIndex)
        binding.savePagesLocallyCheckBox.isChecked = savePagesLocally
        binding.wordsPerWaveSeekBar.progress = wordsPerWave
         binding.wordsPerWaveTextView.text = getString(R.string.words_per_wave, wordsPerWave)

        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isEmpty()){
                    errorMessageTextView.visibility = View.VISIBLE
                    errorMessageTextView.text = "Veuillez entrer un prénom d'au moins 1 caractère"
                }
                else{
                    errorMessageTextView.visibility = View.GONE
                    prefs.edit().putString("name", s.toString()).apply()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.favoriteDictionarySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.wordsPerWaveSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                 binding.wordsPerWaveTextView.text = getString(R.string.words_per_wave, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                prefs.edit().putInt("words_per_wave", seekBar.progress).apply()
            }
        })

        binding.notificationFrequencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedFrequency = parent?.getItemAtPosition(position).toString()
                prefs.edit().putString("notification_frequency", selectedFrequency).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        learningLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent?.selectedItem.toString()
                if(selectedLanguage == SpinnerValMem){
                    val newPos = ArrayLanguage.indexOf(Spinner2ValMem)
                    learningSourceLanguageSpinner.setSelection(newPos)
                    prefs.edit().putString("learning_Sourcelanguage", Spinner2ValMem).apply()

                }
                SpinnerValMem = learningSourceLanguageSpinner.selectedItem.toString()
                Spinner2ValMem = learningLanguageSpinner.selectedItem.toString()
                prefs.edit().putString("learning_language", selectedLanguage).apply()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        learningSourceLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent?.selectedItem.toString()
                if(selectedLanguage == Spinner2ValMem){
                    val newPos = ArrayLanguage.indexOf(SpinnerValMem)
                    learningLanguageSpinner.setSelection(newPos)
                    prefs.edit().putString("learning_language", SpinnerValMem).apply()
                }
                SpinnerValMem = learningSourceLanguageSpinner.selectedItem.toString()
                Spinner2ValMem = learningLanguageSpinner.selectedItem.toString()
                prefs.edit().putString("learning_Sourcelanguage", selectedLanguage).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }






    }
}



