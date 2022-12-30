package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.room.Room

class ReceiveHTML : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_html)

        if(intent.action == Intent.ACTION_SEND){
            val receivedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (receivedText != null) {
                val builder = AlertDialog.Builder(this)
                val word = intent.getStringExtra("word2translate")
                var sourceLanguage =intent.getStringExtra("LangSource")
                var targetLanguage = intent.getStringExtra("Lang2Trad")
                builder.setTitle("Ajouter le mot " + word+ " à la base de donnée (" + sourceLanguage +"->"+ targetLanguage+")")
                builder.setMessage("Voulez vous ajouter la traduction de " + word+ " à la base de donnée ?")

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

    fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quelle traduction avez vous trouvé pour " + intent.getStringExtra("word2translate"))

        val input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("Ajouter", DialogInterface.OnClickListener { dialog, which ->
            val inputText = input.text.toString()
            val db = Room.databaseBuilder(
                this,
                LanguageDatabase::class.java, "languageDB3"
            ).allowMainThreadQueries().build()



            var highestId = db.languageDao.getMaxId()

            if (highestId == null)
                highestId = 0
            else{
                highestId++
            }
            val sourceLanguage = intent.getStringExtra("LangSource")
            val targetLanguage = intent.getStringExtra("Lang2Trad")
            var langue1 = ""
            var langue2 = ""
            val MotATraduire = intent.getStringExtra("word2translate")
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
            var LienAB = "https://www.google.com/search?q=$MotATraduire+translation+$langue1+to+$langue2"
            var LienBA = "https://www.google.com/search?q=$inputText+translation+$langue2+to+$langue1"
            if(intent.getStringExtra("LinkName")!! == "Reverso"){
                LienAB = "https://dictionnaire.reverso.net/$langue1-$langue2/$MotATraduire"
                LienBA = "https://dictionnaire.reverso.net/$langue2-$langue1/$inputText"
            }
            else if(intent.getStringExtra("LinkName")!! == "Wordreference"){
                LienAB = intent.getStringExtra("Link")!!+sourceLanguage+targetLanguage+"/"+MotATraduire
                LienBA = intent.getStringExtra("Link")!!+targetLanguage+sourceLanguage+"/"+inputText

            }
            else {

            }
            val AtoB = Language(
                id = highestId,
                MotSource = MotATraduire,
                MotDestination = inputText,
                LangueSource = sourceLanguage,
                LangueDestination = targetLanguage,
                Lien = LienAB,
                NomDictionnaire = intent.getStringExtra("LinkName")!!
            )
            db.languageDao.insert(AtoB)
            val BtoA = Language(
                id = highestId+1,
                MotSource = inputText,
                MotDestination = MotATraduire,
                LangueSource = targetLanguage,
                LangueDestination = sourceLanguage,
                Lien = LienBA,
                NomDictionnaire = intent.getStringExtra("LinkName")!!
            )
            db.languageDao.insert(BtoA)
            finish()

            
        })
        builder.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            finish()

        })

        builder.show()
    }
}