package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import java.net.URL

class ReceiveHTML : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_html)

        if(intent.action == Intent.ACTION_SEND){
            val receivedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (receivedText != null) {
                if(intent.getStringExtra("word2translate") == null){
                    addFromExternalWebSite()
                }
                else {
                    val builder = AlertDialog.Builder(this)
                    val word = intent.getStringExtra("word2translate")
                    var sourceLanguage = intent.getStringExtra("LangSource")
                    var targetLanguage = intent.getStringExtra("Lang2Trad")
                    builder.setTitle("Ajouter le mot " + word + " à la base de donnée (" + sourceLanguage + "->" + targetLanguage + ")")
                    builder.setMessage("Voulez vous ajouter la traduction de " + word + " à la base de donnée ?")

                    builder.setPositiveButton("Oui") { _, _ ->
                        showInputDialog()
                    }

                    builder.setNegativeButton("Non") { _, _ ->
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
            }
        }
    }

    private fun addFromExternalWebSite() {
        val sourceLanguageSpinner = Spinner(this)
        val targetLanguageSpinner = Spinner(this)
        val translationInput = EditText(this)
        val WordInput = EditText(this)
        val websiteFrom = EditText(this)
        val errorL = TextView (this)
        val url = URL(intent.getStringExtra(Intent.EXTRA_TEXT)).host.split(".")
        val website = url[url.size - 2]
        websiteFrom.setText(website)



        val languages = listOf("Francais", "Anglais", "Espagnol", "Allemand")
        val lang4bdd = listOf("fr","en","es","de")
        val sourceLanguageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        sourceLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceLanguageSpinner.adapter = sourceLanguageAdapter
        val targetLanguageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        targetLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        targetLanguageSpinner.adapter = targetLanguageAdapter
        targetLanguageSpinner.setSelection(1)

        


        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Ajouter un mot")
            .setView(LinearLayout(this@ReceiveHTML).apply {
                orientation = LinearLayout.VERTICAL
                addView(TextView(this@ReceiveHTML).apply {
                    text = "Langue source"
                    setPadding(16, 16, 16, 8)
                })
                addView(sourceLanguageSpinner)
                addView(TextView(this@ReceiveHTML).apply {
                    text = "Langue destination"
                    setPadding(16, 16, 16, 8)
                })
                addView(targetLanguageSpinner)
                addView(TextView(this@ReceiveHTML).apply {
                    text = "Mot à traduire"
                    setPadding(16, 16, 16, 8)
                })
                addView(WordInput)
                addView(TextView(this@ReceiveHTML).apply {
                    text = "Mot Traduit"
                    setPadding(16, 16, 16, 8)
                })
                addView(translationInput)
                addView(errorL.apply {
                    text = "Site d'origine"
                    setPadding(16, 16, 16, 8)
                })
                addView(websiteFrom)
                addView(TextView(this@ReceiveHTML).apply {
                    text = "Merci de bien vouloir tout remplir"
                    setPadding(16, 16, 16, 8)
                    visibility = View.GONE
                })
            })
            .setPositiveButton("Ajouter"){ dialog, _ ->
                val db = Room.databaseBuilder(
                    this,
                    LanguageDatabase::class.java, "languageDB3"
                ).allowMainThreadQueries().build()

                val inputTextForBDD = translationInput.text.toString().lowercase().substring(0,1).uppercase()+translationInput.text.toString().lowercase().substring(1)
                val motATraduireForBDD = translationInput.text.toString().lowercase().substring(0,1).uppercase()+translationInput.text.toString().lowercase().substring(1)

                val sourceLanguage = lang4bdd[sourceLanguageSpinner.selectedItemPosition]
                val targetLanguage = lang4bdd[targetLanguageSpinner.selectedItemPosition]
                val langue2add = LanguageItem(
                    MotSource = translationInput.text.toString(),
                    MotDestination = motATraduireForBDD,
                    LangueSource = sourceLanguage,
                    LangueDestination = targetLanguage,
                    Lien = intent.getStringExtra(Intent.EXTRA_TEXT)!!,
                    NomDictionnaire = websiteFrom.text.toString(),
                    isFileSaved = false,
                    isInNotification = false
                )
                db.languageDao.insert(langue2add)
                Toast.makeText(this, "Mot ajouté..", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                db.close()
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Annuler"){ dialog, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).visibility = View.GONE
        WordInput.addTextChangedListener {
            if(translationInput.text.trim().toString().isNotEmpty() && WordInput.text.trim().toString().isNotEmpty() && websiteFrom.text.toString().isNotEmpty()){
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).visibility = View.VISIBLE
            }
            else{
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).visibility = View.GONE
            }
        }

        translationInput.addTextChangedListener {
            if(translationInput.text.trim().toString().isNotEmpty() && WordInput.text.trim().toString().isNotEmpty() && websiteFrom.text.toString().isNotEmpty()){
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).visibility = View.VISIBLE
            }
            else{
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).visibility = View.GONE
            }
        }

        websiteFrom.addTextChangedListener {
            if(translationInput.text.trim().toString().isNotEmpty() && WordInput.text.trim().toString().isNotEmpty() && websiteFrom.text.toString().isNotEmpty()){
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).visibility = View.VISIBLE
            }
            else{
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).visibility = View.GONE
            }
        }


        /*dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val sourceLanguage = sourceLanguageSpinner.selectedItem as String
            val targetLanguage = targetLanguageSpinner.selectedItem as String
            val translation = translationInput.text.toString()
            if (translation.isNotEmpty()) {
                // Add the word to the database
                // Show a toast to confirm the word has been added
            } else {
                // Show an error message
            }
        }*/

                }
    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quelle traduction avez vous trouvé pour " + intent.getStringExtra("word2translate"))

        val input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("Ajouter") { _, _ ->
            val inputText = input.text.toString()
            val db = Room.databaseBuilder(
                this,
                LanguageDatabase::class.java, "languageDB3"
            ).allowMainThreadQueries().build()


            val sourceLanguage = intent.getStringExtra("LangSource")
            val targetLanguage = intent.getStringExtra("Lang2Trad")
            var langue1 = ""
            var langue2 = ""
            val motATraduire = intent.getStringExtra("word2translate")?.lowercase()
            if (sourceLanguage == "fr") {
                langue1 = "french"
            } else if (sourceLanguage == "es") {
                langue1 = "spanish"
            } else if (sourceLanguage == "de") {
                langue1 = "german"
            } else if (sourceLanguage == "en") {
                langue1 = "english"
            }
            if (targetLanguage == "fr") {
                langue2 = "french"
            } else if (targetLanguage == "es") {
                langue2 = "spanish"
            } else if (targetLanguage == "de") {
                langue2 = "german"
            } else if (targetLanguage == "en") {
                langue2 = "english"
            }
            var lienAB =
                "https://www.google.com/search?q=$motATraduire+translation+$langue1+to+$langue2"
            var lienBA =
                "https://www.google.com/search?q=$inputText+translation+$langue2+to+$langue1"
            if (intent.getStringExtra("LinkName")!! == "Reverso") {
                lienAB = "https://dictionnaire.reverso.net/$langue1-$langue2/$motATraduire"
                lienBA = "https://dictionnaire.reverso.net/$langue2-$langue1/$inputText"
            } else if (intent.getStringExtra("LinkName")!! == "Wordreference") {
                lienAB =
                    intent.getStringExtra("Link")!! + sourceLanguage + targetLanguage + "/" + motATraduire
                lienBA =
                    intent.getStringExtra("Link")!! + targetLanguage + sourceLanguage + "/" + inputText

            } else { //TODO CHECK FOR OTHER DICTIONNAIRES

            }
            val inputTextForBDD = inputText.lowercase().substring(0,1).uppercase()+inputText.lowercase().substring(1)
            val motATraduireForBDD = motATraduire!!.lowercase().substring(0,1).uppercase()+motATraduire.lowercase().substring(1)
            val atoB = LanguageItem(
                MotSource = motATraduireForBDD,
                MotDestination = inputTextForBDD,
                LangueSource = sourceLanguage,
                LangueDestination = targetLanguage,
                Lien = lienAB,
                NomDictionnaire = intent.getStringExtra("LinkName")!!,
                isFileSaved = false,
                isInNotification = false
            )
            db.languageDao.insert(atoB)
            val btoA = LanguageItem(
                MotSource = inputTextForBDD,
                MotDestination = motATraduireForBDD,
                LangueSource = targetLanguage,
                LangueDestination = sourceLanguage,
                Lien = lienBA,
                NomDictionnaire = intent.getStringExtra("LinkName")!!,
                isFileSaved = false,
                isInNotification = false
            )
            db.languageDao.insert(btoA)
            db.close()
            finish()


        }
        builder.setNegativeButton("Annuler") { dialog, _ ->
            dialog.cancel()
            finish()

        }

        builder.show()
    }
}