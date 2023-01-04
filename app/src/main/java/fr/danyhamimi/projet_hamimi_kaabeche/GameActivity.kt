package fr.danyhamimi.projet_hamimi_kaabeche

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        var BestScore = preferences.getString("bestscore", "0")


        binding.bestScoreTextView.setText("Meilleur score : " + BestScore)
        binding.backToMenuButton.setOnClickListener {
            finish()
        }

        binding.playButton.setOnClickListener {
            val intent = Intent(this, InGameActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}