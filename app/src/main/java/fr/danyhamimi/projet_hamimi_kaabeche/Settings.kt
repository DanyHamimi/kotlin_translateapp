package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.ActivitySettingsBinding
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

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
                val db = Room.databaseBuilder(
                    this,
                    LanguageDatabase::class.java, "languageDB3"
                ).allowMainThreadQueries().build()
                if(binding.savePagesLocallyCheckBox.isChecked){
                    val AllLanguagesToSave = db.languageDao.getAllLanguages()
                    val AllLinksToDownload = ArrayList<URL>()
                    val ListIDWords = ArrayList<Long>()
                    // create an array with all links to download by gettings them in AllLanguagesToSave
                    for (language in AllLanguagesToSave) {
                        if(language.isFileSaved == false){
                            val link = language.Lien
                            ListIDWords.add(language.id)
                            db.languageDao.updateIsSaved(true,language.id)
                            AllLinksToDownload.add(URL(link))
                        }
                    }
                    val sharedStorage = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
                    DownloadTask(sharedStorage,ListIDWords).execute(LinkedList(AllLinksToDownload))
                }
                else{
                    val AllLanguagesToSave = db.languageDao.getAllLanguages()
                    for (language in AllLanguagesToSave) {
                        db.languageDao.updateIsSaved(false,language.id)
                    }
                    val filesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
                    val files = filesDir.listFiles()
                    for (file in files) {
                        file.delete()
                    }
                }
                val startingHourPicker = binding.startingHourPicker
                val calendar = Calendar.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val nameNotif = "My Channel"
                    val descriptionText = "This is my channel"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel("CHANNEL_IDDANY", nameNotif, importance).apply {
                        description = descriptionText
                    }
                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }
                val notificationSenderIntent = Intent(this, NotificationSender::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationSenderIntent, PendingIntent.FLAG_IMMUTABLE)


                val frequency = binding.notificationFrequencySpinner.selectedItem.toString()
                if (frequency == "Jamais") {
                    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmManager.cancel(pendingIntent)
                } else {
                    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val interval = when (frequency) {
                        "Toutes les minutes" -> 60 * 1000
                        "Toutes les heures" -> 60 * 60 * 1000
                        "Toutes les 12 heures" -> 12 * 60 * 60 * 1000
                        "Tous les jours" -> 24 * 60 * 60 * 1000
                        else -> 1 * 60 * 1000
                    }
                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        interval.toLong(),
                        pendingIntent
                    )
                }
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 60000, pendingIntent)

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



