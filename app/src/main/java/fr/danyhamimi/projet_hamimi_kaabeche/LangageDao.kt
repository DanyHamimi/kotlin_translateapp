package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LanguageDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(language: Language)

    @Query("SELECT * from language_table")
    fun getAllLanguages(): LiveData<List<Language>>

    @Query("SELECT * from language_table WHERE id = :key")
    fun getLanguageById(key: Long): Language?

    @Query("SELECT * from language_table WHERE MotFR = :key")
    fun getLanguageByFR(key: String): Language?

    @Query("SELECT * from language_table WHERE MotAnglais = :key")
    fun getLanguageByEN(key: String): Language?

    @Query("SELECT * from language_table WHERE MotEspagnol = :key")
    fun getLanguageByES(key: String): Language?

    @Query("SELECT * from language_table WHERE MotAllemand = :key")
    fun getLanguageByDE(key: String): Language?

    @Query("SELECT MotFR from language_table WHERE id = :key")
    fun getWord(key: Long): String?

    @Query("SELECT MotAnglais from language_table WHERE id = :key")
    fun getWordEN(key: Long): String?

    @Query("SELECT MotEspagnol from language_table WHERE id = :key")
    fun getWordES(key: Long): String?

    @Query("SELECT MotAllemand from language_table WHERE id = :key")
    fun getWordDE(key: Long): String?

    @Query("SELECT id from language_table WHERE MotFR = :key")
    fun getId(key: String): Long?

    @Query("SELECT id from language_table WHERE MotAnglais = :key")
    fun getIdEN(key: String): Long?

    @Query("SELECT id from language_table WHERE MotEspagnol = :key")
    fun getIdES(key: String): Long?

    @Query("SELECT id from language_table WHERE MotAllemand = :key")
    fun getIdDE(key: String): Long?





}

