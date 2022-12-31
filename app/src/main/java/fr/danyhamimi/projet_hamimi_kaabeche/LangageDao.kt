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

    // Get the word by MotSource LangueSource LangueDestination 
    @Query("SELECT * from language_table WHERE LOWER(MotSource) = LOWER(:key1) AND LOWER(LangueSource) = LOWER(:key2) AND LOWER(LangueDestination) = LOWER(:key3)   ")
    fun getLanguageByMotSource(key1: String, key2: String, key3: String): Language?

    // Get the heighest id
    @Query("SELECT MAX(id) from language_table")
    fun getMaxId(): Long?

    //Delete language by the MotSource and LangueSource and LangueDestination
    @Query("DELETE FROM language_table WHERE LOWER(MotSource) = LOWER(:key1) AND LOWER(LangueSource) = LOWER(:key2) AND LOWER(LangueDestination) = LOWER(:key3)")
    fun deleteLanguageByMotSource(key1: String, key2: String, key3: String)

    //Delete Language by the MotDestination and LangueSource and LangueDestination
    @Query("DELETE FROM language_table WHERE LOWER(MotDestination) = LOWER(:key1) AND LOWER(LangueSource) = LOWER(:key2) AND LOWER(LangueDestination) = LOWER(:key3)")
    fun deleteLanguageByMotDestination(key1: String, key2: String, key3: String)

    //Select all words from the database where the language is the same as the one selected and the destination language is the same as the one selected
    @Query("SELECT * from language_table WHERE LOWER(LangueSource) = LOWER(:key1) AND LOWER(LangueDestination) = LOWER(:key2)")
    fun getLanguageByLangueSource(key1: String, key2: String): List<Language>

    //Insert all
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg languages: Language)

    //Get all language by their dictionary
    @Query("SELECT * from language_table WHERE LOWER(NomDictionnaire) = LOWER(:key)")
    fun getLanguageByDictionnaire(key: String): List<Language>






}

