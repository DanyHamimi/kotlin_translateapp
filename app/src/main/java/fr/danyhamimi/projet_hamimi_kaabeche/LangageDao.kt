package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.lifecycle.LiveData
import androidx.room.*

// This is the DAO class, which defines the methods for accessing the database
@Dao
interface LanguageDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(language: Language)

    @Query("SELECT * from language_table")
    fun getAllLanguages(): LiveData<List<Language>>

    //Get the language by id
    @Query("SELECT * from language_table WHERE id = :key")
    fun getLanguageById(key: Long): Language?

    // Check if the word is in the database France -> FR
    @Query("SELECT * from language_table WHERE MotFR = :key")
    fun getLanguageByFR(key: String): Language?

    // Check if the word is in the database English -> EN
    @Query("SELECT * from language_table WHERE MotAnglais = :key")
    fun getLanguageByEN(key: String): Language?

    // Check if the word is in the database Spanish -> ES
    @Query("SELECT * from language_table WHERE MotEspagnol = :key")
    fun getLanguageByES(key: String): Language?

    // Check if the word is in the database German -> DE
    @Query("SELECT * from language_table WHERE MotAllemand = :key")
    fun getLanguageByDE(key: String): Language?

    // Get the word in the db by id
    @Query("SELECT MotFR from language_table WHERE id = :key")
    fun getWord(key: Long): String?

    // Get the word in the db by id
    @Query("SELECT MotAnglais from language_table WHERE id = :key")
    fun getWordEN(key: Long): String?

    // Get the word in the db by id
    @Query("SELECT MotEspagnol from language_table WHERE id = :key")
    fun getWordES(key: Long): String?

    // Get the word in the db by id
    @Query("SELECT MotAllemand from language_table WHERE id = :key")
    fun getWordDE(key: Long): String?

    // Get the id of the word
    @Query("SELECT id from language_table WHERE MotFR = :key")
    fun getId(key: String): Long?

    // Get the id of the word
    @Query("SELECT id from language_table WHERE MotAnglais = :key")
    fun getIdEN(key: String): Long?

    // Get the id of the word
    @Query("SELECT id from language_table WHERE MotEspagnol = :key")
    fun getIdES(key: String): Long?

    // Get the id of the word
    @Query("SELECT id from language_table WHERE MotAllemand = :key")
    fun getIdDE(key: String): Long?





}

