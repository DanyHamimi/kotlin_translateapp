package fr.danyhamimi.projet_hamimi_kaabeche

import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import androidx.room.Room
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.ActivityInGameBinding

class InGameActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityInGameBinding
    private lateinit var BestScore : String
    private lateinit var preferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        binding = ActivityInGameBinding.inflate(layoutInflater)


        setContentView(binding.root)
        preferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        BestScore = preferences.getString("bestscore", "0")!!
        var LearningSource = preferences.getString("learning_Sourcelanguage", "Francais")
        var learningLanguage = preferences.getString("learning_language", "Anglais")
        if (LearningSource == "Francais") {
            LearningSource = "fr"
        }
        if (LearningSource == "Anglais") {
            LearningSource = "en"
        }
        if (LearningSource == "Espagnol") {
            LearningSource = "es"
        }
        if (LearningSource == "Allemand") {
            LearningSource = "de"
        }
        if (learningLanguage == "Francais") {
            learningLanguage = "fr"
        }
        if (learningLanguage == "Anglais") {
            learningLanguage = "en"
        }
        if (learningLanguage == "Espagnol") {
            learningLanguage = "es"
        }
        if (learningLanguage == "Allemand") {
            learningLanguage = "de"
        }

        binding.bestScoreTextView.setText("Meilleur score : "+BestScore )

        val db = Room.databaseBuilder(
            this,
            LanguageDatabase::class.java, "languageDB3"
        ).allowMainThreadQueries().build()

        launchGame(0,db,LearningSource!!,learningLanguage!!)

        binding.backButton.setOnClickListener {
            finish()
            db.close()
        }
        binding.restartButton.setOnClickListener {
            launchGame(0,db,LearningSource,learningLanguage)
        }

    }

    private fun launchGame(i: Int, db: LanguageDatabase, LearningSource: String, learningLanguage: String
    ) {
        var RealLearningSource = preferences.getString("learning_Sourcelanguage", "Francais")
        var ReallearningLanguage = preferences.getString("learning_language", "Anglais")
        binding.answer0Button.setBackgroundColor(Color.BLACK)
        binding.answer1Button.setBackgroundColor(Color.BLACK)
        binding.answer2Button.setBackgroundColor(Color.BLACK)
        binding.answer3Button.setBackgroundColor(Color.BLACK)
        if (i == 0) {
            binding.restartButton.visibility = View.GONE
            binding.currentScoreTextView.setText("Score : 0")
        }
        val languages = db.languageDao.getLanguageByLangueSource(LearningSource,learningLanguage)
        if (languages.size < 4) {
            binding.questionTextView.visibility = View.VISIBLE
            binding.questionTextView.setText("Pas assez de mots dans la BDD pour jouer dans cette langue")
            binding.answer0Button.visibility = View.GONE
            binding.answer1Button.visibility = View.GONE
            binding.answer2Button.visibility = View.GONE
            binding.answer3Button.visibility = View.GONE
            binding.restartButton.visibility = View.GONE
            return
        }

        val randomLanguages = languages.shuffled().take(4)
        binding.answer0Button.setText(randomLanguages[0].MotDestination)
        binding.answer1Button.setText(randomLanguages[1].MotDestination)
        binding.answer2Button.setText(randomLanguages[2].MotDestination)
        binding.answer3Button.setText(randomLanguages[3].MotDestination)

        val random = (0..3).random()
        val motAtraduire = randomLanguages[random].MotSource!!
        binding.questionTextView.setText("Quelle est la traduction du mot "+ RealLearningSource!!.lowercase()+ " " +motAtraduire+ " en "+ ReallearningLanguage!!.lowercase())

        binding.answer0Button.setOnClickListener {
            CheckAnswer(random,0,i,LearningSource,learningLanguage,db,motAtraduire)
        }
        binding.answer1Button.setOnClickListener {
            CheckAnswer(random,1,i,LearningSource,learningLanguage,db,motAtraduire)
        }
        binding.answer2Button.setOnClickListener {
            CheckAnswer(random,2,i,LearningSource,learningLanguage,db,motAtraduire)
        }
        binding.answer3Button.setOnClickListener {
            CheckAnswer(random,3,i,LearningSource,learningLanguage,db,motAtraduire)
        }

    }

    private fun CheckAnswer(random: Int, i: Int, score: Int, LearningSource: String, learningLanguage: String, db: LanguageDatabase, motaTraduire: String) {
        var goodans = ""
        binding.answer0Button.setOnClickListener(null)
        binding.answer1Button.setOnClickListener(null)
        binding.answer2Button.setOnClickListener(null)
        binding.answer3Button.setOnClickListener(null)
        if (random == i) {
            val scoreNew = score+1
            binding.currentScoreTextView.setText("Score : "+ scoreNew.toString())
            if(scoreNew > BestScore.toInt()){
                binding.bestScoreTextView.setText("Meilleur score: "+ scoreNew)
                val preferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("bestscore",scoreNew.toString())
                editor.apply()
            }
            when (i) {
                0 -> {
                    binding.answer0Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer0Button.text.toString()
                }
                1 -> {
                    binding.answer1Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer1Button.text.toString()
                }
                2 -> {
                    binding.answer2Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer2Button.text.toString()
                }
                3 -> {
                    binding.answer3Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer3Button.text.toString()
                }
            }
            binding.nextQuestion.visibility = View.VISIBLE
            binding.questionTextView.setText("Bonne réponse, la traduction de "+ motaTraduire +" était bien "+ goodans)
            binding.nextQuestion.setOnClickListener {
                binding.nextQuestion.visibility = View.GONE
                launchGame(scoreNew,db,LearningSource,learningLanguage)
            }
        } else {
            binding.currentScoreTextView.setText("PERDU")

            when (i) {
                0 -> {
                    binding.answer0Button.setBackgroundColor(Color.RED)
                }
                1 -> {
                    binding.answer1Button.setBackgroundColor(Color.RED)
                }
                2 -> {
                    binding.answer2Button.setBackgroundColor(Color.RED)
                }
                3 -> {
                    binding.answer3Button.setBackgroundColor(Color.RED)
                }
            }

            when (random){
                0 -> {
                    binding.answer0Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer0Button.text.toString()
                }
                1 -> {
                    binding.answer1Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer1Button.text.toString()
                }
                2 -> {
                    binding.answer2Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer2Button.text.toString()
                }
                3 -> {
                    binding.answer3Button.setBackgroundColor(Color.GREEN)
                    goodans = binding.answer3Button.text.toString()
                }
            }
            binding.restartButton.visibility = View.VISIBLE
            binding.questionTextView.setText("Mauvaise réponse, la traduction de "+ motaTraduire +" était : "+ goodans)

        }
    }

}