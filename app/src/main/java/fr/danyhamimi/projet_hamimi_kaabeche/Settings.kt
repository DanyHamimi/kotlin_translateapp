package fr.danyhamimi.projet_hamimi_kaabeche

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class Settings : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var favoriteDictionarySpinner: Spinner
    private lateinit var savePagesLocallyCheckBox : CheckBox
    private lateinit var wordsPerWaveSeekBar: SeekBar
    private lateinit var wordsPerWaveTextView: TextView
    private lateinit var notificationFrequencySpinner: Spinner
    private lateinit var SaveSettings: Button
    private lateinit var ExitSettings: Button


    private val PREFS_NAME = "app_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        nameEditText = findViewById(R.id.name_edit_text)
        favoriteDictionarySpinner = findViewById(R.id.favorite_dictionary_spinner)
        savePagesLocallyCheckBox = findViewById(R.id.save_pages_locally_check_box)
        wordsPerWaveSeekBar = findViewById(R.id.words_per_wave_seek_bar)
        wordsPerWaveTextView = findViewById(R.id.words_per_wave_text_view)
        notificationFrequencySpinner = findViewById(R.id.notification_frequency_spinner)
        SaveSettings = findViewById(R.id.save_button)
        ExitSettings = findViewById(R.id.cancel_button)
        val errorMessageTextView = findViewById<TextView>(R.id.error_message)

        SaveSettings.setOnClickListener(View.OnClickListener {
            if (nameEditText.text.toString().isEmpty()) {
                errorMessageTextView.visibility = View.VISIBLE
                errorMessageTextView.text = "Veuillez entrer un prénom d'au moins 1 caractère"
            } else {
                errorMessageTextView.visibility = View.GONE
                val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("name", nameEditText.text.toString())
                editor.putString("favorite_dictionary", favoriteDictionarySpinner.selectedItem.toString())
                editor.putBoolean("save_pages_locally", savePagesLocallyCheckBox.isChecked)
                editor.putInt("words_per_wave", wordsPerWaveSeekBar.progress)
                editor.putString("notification_frequency", notificationFrequencySpinner.selectedItem.toString())
                editor.apply()
                //TODO download all webpages from database
                finish()
            }
        })





        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
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

        notificationFrequencySpinner.adapter = notificationFrequencyAdapter

        val notificationFrequency = prefs.getString("notification_frequency", "Tous les jours")

        val notificationFrequencyPosition = notificationFrequencyAdapter.getPosition(notificationFrequency)
        notificationFrequencySpinner.setSelection(notificationFrequencyPosition)
        val learningLanguageSpinner: Spinner = findViewById(R.id.learning_language_spinner)

        val learningLanguageAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.languages,
            android.R.layout.simple_spinner_item
        )
        learningLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        learningLanguageSpinner.adapter = learningLanguageAdapter

        val learningLanguage = prefs.getString("learning_language", "Anglais")

        val learningLanguagePosition = learningLanguageAdapter.getPosition(learningLanguage)
        learningLanguageSpinner.setSelection(learningLanguagePosition)


        nameEditText.setText(name)
        val dictionaries = resources.getStringArray(R.array.dictionaries)
        val favoriteDictionaryIndex = dictionaries.indexOf(favoriteDictionary)
        favoriteDictionarySpinner.setSelection(favoriteDictionaryIndex)
        savePagesLocallyCheckBox.isChecked = savePagesLocally
        wordsPerWaveSeekBar.progress = wordsPerWave
        wordsPerWaveTextView.text = getString(R.string.words_per_wave, wordsPerWave)

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                prefs.edit().putString("name", s.toString()).apply()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        favoriteDictionarySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        wordsPerWaveSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                wordsPerWaveTextView.text = getString(R.string.words_per_wave, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                prefs.edit().putInt("words_per_wave", seekBar.progress).apply()
            }
        })

        notificationFrequencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedFrequency = parent?.getItemAtPosition(position).toString()
                prefs.edit().putString("notification_frequency", selectedFrequency).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        learningLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent?.getItemAtPosition(position).toString()
                prefs.edit().putString("learning_language", selectedLanguage).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }






    }
}



