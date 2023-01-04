package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.ActivitySettingsBinding

class ManageDictionaryActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_dictionary)
        layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = layoutManager

        spinner = findViewById(R.id.spinner)
        val db = Room.databaseBuilder(
            this,
            LanguageDatabase::class.java, "languageDB3"
        ).allowMainThreadQueries().build()

        val getAllDictionnaire = db.languageDao.getAllDictionnaire()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, getAllDictionnaire)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val getAllLanguages = db.languageDao.getLanguageByDictionnaire(spinner.selectedItem.toString())
                val sourceLanguages = ArrayList<String>()
                val destinationLanguages = ArrayList<String>()
                val words = ArrayList<String>()
                val translatedWords = ArrayList<String>()
                val translationWebsite = ArrayList<String>()
                val ids = ArrayList<String>()
                for (i in 0 until getAllLanguages.size) {
                    ids.add(getAllLanguages[i].LangueSource!!)
                    sourceLanguages.add(getAllLanguages[i].LangueSource!!)
                    destinationLanguages.add(getAllLanguages[i].LangueDestination!!)
                    words.add(getAllLanguages[i].MotSource!!)
                    translatedWords.add(getAllLanguages[i].MotDestination!!)
                    translationWebsite.add(getAllLanguages[i].NomDictionnaire)
                }
                val adapter = RecyclerAdapter(sourceLanguages, destinationLanguages, words, translatedWords,translationWebsite,ids,db)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@ManageDictionaryActivity)


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


}