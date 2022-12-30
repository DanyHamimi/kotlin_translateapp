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

    @Query("SELECT * from language_table WHERE MotSource = :key1 AND LangueSource = :key2 AND LangueDestination = :key3")
    fun getLanguageByMotSource(key1: String, key2: String, key3: String): Language?

    @Query("SELECT MAX(id) from language_table")
    fun getMaxId(): Long?

    @Query("DELETE FROM language_table WHERE MotSource = :key1 AND LangueSource = :key2 AND LangueDestination = :key3")
    fun deleteLanguageByMotSource(key1: String, key2: String, key3: String)

    @Query("DELETE FROM language_table WHERE MotDestination = :key1 AND LangueSource = :key2 AND LangueDestination = :key3")
    fun deleteLanguageByMotDestination(key1: String, key2: String, key3: String)





}

