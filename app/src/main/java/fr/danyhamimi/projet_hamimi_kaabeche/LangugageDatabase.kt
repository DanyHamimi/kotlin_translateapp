package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.room.Database
import androidx.room.RoomDatabase

// This is the Room database class, which defines the database and its version
@Database(entities = [Language::class], version = 1, exportSchema = false)
abstract class LanguageDatabase : RoomDatabase() {
    abstract val languageDao: LanguageDao
}