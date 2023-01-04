package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LanguageDao {
    @Insert (entity = Language::class,onConflict = OnConflictStrategy.REPLACE)
    fun insert(language: LanguageItem)

    @Query("SELECT * from language_table")
    fun getAllLanguages(): List<Language>

    @Query("SELECT * from language_table WHERE id = :key")
    fun getLanguageById(key: Long): Language?

    @Query("SELECT * from language_table WHERE LOWER(MotSource) = LOWER(:key1) AND LOWER(LangueSource) = LOWER(:key2) AND LOWER(LangueDestination) = LOWER(:key3)   ")
    fun getLanguageByMotSource(key1: String, key2: String, key3: String): Language?

    @Query("SELECT MAX(id) from language_table")
    fun getMaxId(): Long?

    @Query("DELETE FROM language_table WHERE LOWER(MotSource) = LOWER(:key1) AND LOWER(LangueSource) = LOWER(:key2) AND LOWER(LangueDestination) = LOWER(:key3)")
    fun deleteLanguageByMotSource(key1: String, key2: String, key3: String)

    @Query("DELETE FROM language_table WHERE LOWER(MotDestination) = LOWER(:key1) AND LOWER(LangueSource) = LOWER(:key2) AND LOWER(LangueDestination) = LOWER(:key3)")
    fun deleteLanguageByMotDestination(key1: String, key2: String, key3: String)

    @Query("SELECT * from language_table WHERE LOWER(LangueSource) = LOWER(:key1) AND LOWER(LangueDestination) = LOWER(:key2)")
    fun getLanguageByLangueSource(key1: String, key2: String): List<Language>

    @Insert(entity = Language::class,onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg languages: LanguageItem)

    @Query("SELECT * from language_table WHERE LOWER(NomDictionnaire) = LOWER(:key)")
    fun getLanguageByDictionnaire(key: String): List<Language>

    @Query("SELECT DISTINCT NomDictionnaire from language_table")
    fun getAllDictionnaire(): List<String>

    @Query("UPDATE language_table SET isFileSaved = :key1 WHERE id = :key2")
    fun updateIsSaved(key1: Boolean, key2: Long)





}

